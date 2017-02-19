namespace SDK.Lib
{
    public class CoroutineTaskBase
    {
        protected eCoroutineTaskState mState;
        protected boolean mNeedRemove;

        public CoroutineTaskBase()
        {
            mNeedRemove = true;
        }

        public boolean isRuning()
        {
            return mState == eCoroutineTaskState.eRunning;
        }

        public boolean isPause()
        {
            return mState == eCoroutineTaskState.ePaused;
        }

        public boolean isStop()
        {
            return mState == eCoroutineTaskState.eStopped;
        }

        public void setNeedRemove(boolean value)
        {
            mNeedRemove = value;
        }

        public boolean isNeedRemove()
        {
            return mNeedRemove;
        }

        public void Start()
        {
            mState = eCoroutineTaskState.eRunning;
        }

        public void Stop()
        {
            mState = eCoroutineTaskState.eStopped;
        }

        virtual public void run()
        {
            mState = eCoroutineTaskState.eStopped;
        }
    }
}