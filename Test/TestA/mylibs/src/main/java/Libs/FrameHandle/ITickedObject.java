package Libs.FrameHandle;

public interface ITickedObject
{
    void onTick(float delta, TickMode tickMode);
}