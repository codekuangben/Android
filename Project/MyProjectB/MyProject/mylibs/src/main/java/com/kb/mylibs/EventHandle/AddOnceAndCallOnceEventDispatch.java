package com.kb.mylibs.EventHandle;

public class AddOnceAndCallOnceEventDispatch extends EventDispatch
{
    @Override
    public void addEventHandle(
            ICalleeObject eventListener,
            IDispatchObject eventHandle,
            int eventId
    )
    {
        if (!this.isExistEventHandle(eventListener, eventHandle, eventId))
        {
            super.addEventHandle(eventListener, eventHandle, eventId);
        }
    }

    @Override
    public void dispatchEvent(IDispatchObject dispatchObject)
    {
        super.dispatchEvent(dispatchObject);

        this.clearEventHandle();
    }
}