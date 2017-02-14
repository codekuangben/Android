package SDK.Lib.EventHandle;
{
    public class ResEventDispatch : EventDispatch
    {
        public ResEventDispatch()
        {

        }

        override public void dispatchEvent(IDispatchObject dispatchObject)
        {
            base.dispatchEvent(dispatchObject);

            this.clearEventHandle();
        }
    }
}