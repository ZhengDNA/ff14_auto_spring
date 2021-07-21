#include "ff14_auto_util_WindowCheck.h"
#include "ff14_auto_player_NativeMusicPlayer.h"
#include <windows.h>
#include <tchar.h>

BYTE scan_code(DWORD pKey)
{
    const DWORD res = MapVirtualKey(pKey, MAPVK_VK_TO_VSC);
    return static_cast<BYTE>(res);
}

JNIEXPORT jboolean JNICALL Java_ff14_1auto_util_WindowCheck_isFF14Window(JNIEnv *env, jclass cls)
{
	HWND hwnd = GetForegroundWindow();
    char gameWindowText[] = "◊Ó÷’ª√œÎXIV";
    wchar_t windowText[256];
    GetWindowText(hwnd, reinterpret_cast<LPSTR>(windowText), 256);
    if (!_tcscmp(reinterpret_cast<const char *>(windowText), reinterpret_cast<const char *>(gameWindowText)))
        return 1;
    return 0;
}

JNIEXPORT void JNICALL Java_ff14_1auto_player_NativeMusicPlayer_press(JNIEnv *env, jclass cls, jchar keycode)
{
	keybd_event(keycode, scan_code(keycode), 0, 0);
}

JNIEXPORT void JNICALL Java_ff14_1auto_player_NativeMusicPlayer_release(JNIEnv *env, jclass cls, jchar keycode)
{
	keybd_event(keycode, scan_code(keycode), 2, 0);
}

