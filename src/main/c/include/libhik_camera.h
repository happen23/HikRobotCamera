#ifndef LIB_HIK_CAMERA
#define LIB_HIK_CAMERA

#include <string.h>
#include <stdlib.h>
#include "com_lanplus_lanova_camera_HikCamera.h"
#include "MvCameraControl.h"

#define MAX_CAM_NUM (4UL)

typedef struct _Camera_{
    void *handle;
    char *name;
    int init_state;
    unsigned char *frame_buffer;
    unsigned char *image_buffer;
    int frame_size;
}Camera;

const int    ALLOCATED = 10;
const int    HANDLE_CREATED = 20;
const int    OPENED = 30;
const int    FRAME_BUF_ALLOCATED = 40;
const int    IMAGE_BUF_ALLOCATED = 50;
const int    GRAB_STARTED = 60;

static void release_all();

#endif

