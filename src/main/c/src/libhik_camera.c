#include "libhik_camera.h"

Camera *cameras[MAX_CAM_NUM];

void PrintDeviceInfo(MV_CC_DEVICE_INFO* pstMVDevInfo)
{
    if (NULL == pstMVDevInfo)
    {
        printf("The Pointer of pstMVDevInfo is NULL!\n");
        return;
    }
    if (pstMVDevInfo->nTLayerType == MV_GIGE_DEVICE)
    {
        int nIp1 = ((pstMVDevInfo->SpecialInfo.stGigEInfo.nCurrentIp & 0xff000000) >> 24);
        int nIp2 = ((pstMVDevInfo->SpecialInfo.stGigEInfo.nCurrentIp & 0x00ff0000) >> 16);
        int nIp3 = ((pstMVDevInfo->SpecialInfo.stGigEInfo.nCurrentIp & 0x0000ff00) >> 8);
        int nIp4 = (pstMVDevInfo->SpecialInfo.stGigEInfo.nCurrentIp & 0x000000ff);

        // ch:打印当前相机ip和用户自定义名字 | en:print current ip and user defined name
        printf("Device Model Name: %s\n", pstMVDevInfo->SpecialInfo.stGigEInfo.chModelName);
        printf("CurrentIp: %d.%d.%d.%d\n" , nIp1, nIp2, nIp3, nIp4);
        printf("UserDefinedName: %s\n\n" , pstMVDevInfo->SpecialInfo.stGigEInfo.chUserDefinedName);
    }
    else if (pstMVDevInfo->nTLayerType == MV_USB_DEVICE)
    {
        printf("Device Model Name: %s\n", pstMVDevInfo->SpecialInfo.stUsb3VInfo.chModelName);
        printf("UserDefinedName: %s\n\n", pstMVDevInfo->SpecialInfo.stUsb3VInfo.chUserDefinedName);
    }
    else
    {
        printf("Not support.\n");
    }

    return;
}


JNIEXPORT void JNICALL Java_com_lanplus_lanova_camera_HikCamera_init(JNIEnv *env, jobject obj){
    void* handle = NULL;
    MV_CC_DEVICE_INFO_LIST stDeviceList;
    MVCC_INTVALUE stParam;
    int nRet;
    memset(cameras, 0, sizeof(cameras));
    memset(&stDeviceList, 0, sizeof(MV_CC_DEVICE_INFO_LIST));
    nRet = MV_CC_EnumDevices(MV_GIGE_DEVICE | MV_USB_DEVICE, &stDeviceList);
    if (MV_OK != nRet)
    {
        printf("MV_CC_EnumDevices fail! nRet [%x]\n", nRet);
        return;
    }

    if (stDeviceList.nDeviceNum == 0)
    {
        printf("Find No Devices!\n");
        return;
    }

    for (int i = 0; i < stDeviceList.nDeviceNum; i++)
    {
        printf("[intilizing device %d]:\n", i);
        MV_CC_DEVICE_INFO* pDeviceInfo = stDeviceList.pDeviceInfo[i];
        if (NULL == pDeviceInfo)
        {
            break;
        }
        PrintDeviceInfo(pDeviceInfo);
        // create control data struct
        Camera *pCam = (Camera *)malloc(sizeof(Camera));
        if (pCam == NULL){
            printf("[device %d]'s data struct alloc fail!\n", i);
            release_all();
            return;
        }
        cameras[i] = pCam;
        pCam->init_state = ALLOCATED;
        // create handle
        nRet = MV_CC_CreateHandle(&handle, stDeviceList.pDeviceInfo[i]);
        if (MV_OK != nRet)
        {
            printf("MV_CC_CreateHandle fail! nRet [%x]\n", nRet);
            release_all();
            return;
        }
        pCam->handle = handle;
        pCam->init_state = HANDLE_CREATED;
        // open device
        nRet = MV_CC_OpenDevice(handle, MV_GIGE_DEVICE, 0);
        if (MV_OK != nRet)
        {
            printf("MV_CC_OpenDevice fail! nRet [%x]\n", nRet);
            release_all();
            return;
        }
        pCam->init_state = OPENED;
        // allocate frame buffer
        memset(&stParam, 0, sizeof(MVCC_INTVALUE));
        nRet = MV_CC_GetIntValue(handle, "PayloadSize", &stParam);
        if (MV_OK != nRet)
        {
            printf("Get PayloadSize fail! nRet [0x%x]\n", nRet);
            release_all();
            return;
        }
        pCam->frame_size = stParam.nCurValue;
        pCam->frame_buffer = (unsigned char *)malloc(sizeof(char) * stParam.nCurValue);
        if (NULL == pCam->frame_buffer)
        {
            printf("alloc frame buffer fail! nRet [0x%x]\n", nRet);
            release_all();
            return;
        }
        pCam->init_state = FRAME_BUF_ALLOCATED;
        // allocate image buffer
        pCam->image_buffer = (unsigned char *)malloc(sizeof(char) * stParam.nCurValue);
        if (NULL == pCam->image_buffer)
        {
            printf("alloc image buffer fail! nRet [0x%x]\n", nRet);
            release_all();
            return;
        }
        pCam->init_state = IMAGE_BUF_ALLOCATED;
        // start grabing
        nRet = MV_CC_StartGrabbing(handle);
        if (MV_OK != nRet)
        {
            printf("MV_CC_StartGrabbing fail! nRet [%x]\n", nRet);
            release_all();
            return;
        }
        pCam->init_state = GRAB_STARTED;

        printf("[device %d initlized!]:\n", i);
    }
}

JNIEXPORT jbyteArray JNICALL Java_com_lanplus_lanova_camera_HikCamera_takePhoto (JNIEnv *env, jobject thisObj, jint camIdx){
    Camera *pCam;
    int nRet;
    void *handle;
    MV_FRAME_OUT_INFO_EX stFrameInfo = {0};
    if (cameras[camIdx] == NULL){
        printf("[no device %d!]:\n", camIdx);
        return NULL;
    }
    printf("[capturing device %d!]:\n", camIdx);
    pCam = cameras[camIdx];
    handle = pCam->handle;
    memset(&stFrameInfo, 0, sizeof(MV_FRAME_OUT_INFO_EX));
    // capture image frame
    nRet = MV_CC_GetOneFrameTimeout(handle, pCam->frame_buffer, pCam->frame_size, &stFrameInfo, 1000);
    if (nRet == MV_OK)
    {
        printf("GetOneFrame, Width[%d], Height[%d], nFrameNum[%d]\n",
            stFrameInfo.nWidth, stFrameInfo.nHeight, stFrameInfo.nFrameNum);
    }
    else{
        printf("No data[%x]\n", nRet);
    }
    // encode and compress image frame
    MV_SAVE_IMAGE_PARAM_EX stParam;
    // source params
    stParam.pData         = pCam->frame_buffer;       //原始图像数据
    stParam.nDataLen      = stFrameInfo.nFrameLen;    //原始图像数据长度
    stParam.enPixelType   = stFrameInfo.enPixelType;  //原始图像数据的像素格式
    stParam.nWidth        = stFrameInfo.nWidth;       //图像宽
    stParam.nHeight       = stFrameInfo.nHeight;      //图像高
    stParam.nJpgQuality   = 70;						  //JPEG图片编码质量
    // dest params
    stParam.enImageType   = MV_Image_Jpeg;            //需要保存的图像类型，转换成JPEG格式
    stParam.nBufferSize   = pCam->frame_size;                 //存储节点的大小
   stParam.pImageBuffer  = pCam->image_buffer;                   //输出数据缓冲区，存放转换之后的图片数据
    nRet = MV_CC_SaveImageEx2(handle, &stParam);
    if(MV_OK != nRet)
    {
        printf("encode fail, err = [%x]\n", nRet);
        return NULL;
    }
    jbyteArray outJNIArray = (*env)->NewByteArray(env, stParam.nImageLen);
    if (NULL == outJNIArray) {
        printf("new jbyteArray fail\n");
        return NULL;
    }
    (*env)->SetByteArrayRegion(env, outJNIArray, 0, stParam.nImageLen, pCam->image_buffer);
    return outJNIArray;
}


JNIEXPORT void JNICALL Java_com_lanplus_lanova_camera_HikCamera_destroy(JNIEnv *env, jobject obj){
    release_all();
}

static void release_all(){
    Camera *pCam;
    int nRet;
    void *handle;
    for (int i = 0; i < MAX_CAM_NUM; i ++) {
        if (cameras[i] == NULL)
            break;
        printf("[destroying device %d!]:\n", i);
        pCam = cameras[i];
        handle = pCam->handle;
        // stop grabing
        if (pCam->init_state >= GRAB_STARTED){
            nRet = MV_CC_StopGrabbing(handle);
            if (MV_OK != nRet)
            {
                printf("MV_CC_StopGrabbing fail! nRet [%x]\n", nRet);
            }
        }
        // free image buffer
        if (pCam->init_state >= IMAGE_BUF_ALLOCATED){
            free(pCam->image_buffer);
            pCam->image_buffer = NULL;
        }
        // free frame buffer
        if (pCam->init_state >= FRAME_BUF_ALLOCATED){
            free(pCam->frame_buffer);
            pCam->frame_buffer = NULL;
        }
        // close device
        if (pCam->init_state >= OPENED){
            nRet = MV_CC_CloseDevice(handle);
            if (MV_OK != nRet)
            {
                printf("MV_CC_CloseDevice fail! nRet [%x]\n", nRet);
            }
        }
        // destroy handle
        if (pCam->init_state >= HANDLE_CREATED){
            nRet = MV_CC_DestroyHandle(handle);
            if (MV_OK != nRet)
            {
                printf("MV_CC_DestroyHandle fail! nRet [%x]\n", nRet);
                break;
            }
        }
        // free control data struct
        free(pCam);
        cameras[i] = NULL;

        printf("[device %d destroyed!]:\n", i);
    }
}

