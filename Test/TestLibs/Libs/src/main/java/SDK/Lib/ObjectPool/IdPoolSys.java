package SDK.Lib.ObjectPool;

import SDK.Lib.DataStruct.MDictionary;
import SDK.Lib.DataStruct.MList;
import SDK.Lib.MsgRoute.IRecycle;

/**
 * @brief 有 Id 的缓存池
 */
public class IdPoolSys
{
    protected MDictionary<String, MList<SDK.Lib.MsgRoute.IRecycle>> mId2PoolDic;

    public IdPoolSys()
    {
        this.mId2PoolDic = new MDictionary<String, MList<IRecycle>>();
    }

    public void init()
    {

    }

    public void dispose()
    {

    }

    public IRecycle getObject(String id)
    {
        IRecycle ret = null;

        if (this.mId2PoolDic.ContainsKey(id))
        {
            if (this.mId2PoolDic.get(id).Count() > 0)
            {
                ret = this.mId2PoolDic.get(id).get(0);
                this.mId2PoolDic.get(id).RemoveAt(0);
            }
        }

        return ret;
    }

    public void deleteObj(String id, IRecycle obj)
    {
        if (!this.mId2PoolDic.ContainsKey(id))
        {
            this.mId2PoolDic.set(id, new MList<IRecycle>());
        }

        this.mId2PoolDic.get(id).Add(obj);
    }
}