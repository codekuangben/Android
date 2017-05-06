package SDK.Lib.Core;

public class UniqueNumIdGen
{
    protected int mPreIdx;
    protected int mCurId;

    public UniqueNumIdGen(int baseUniqueId)
    {
        this.mCurId = 0;
    }

    public int genNewId()
    {
        this.mPreIdx = this.mCurId;
        this.mCurId++;
        return this.mPreIdx;
    }

    public int getCurId()
    {
        return this.mCurId;
    }
}