package SDK.Lib.Core;

/**
 * @brief 唯一字符串生成器
 */
public class UniqueStrIdGen extends UniqueNumIdGen
{
    public final String PlayerPrefix = "PL";
    public final String PlayerChildPrefix = "PC";
    public final String PlayerSnowBlockPrefix = "PSM";
    public final String RobotPrefix = "RT";
    public final String SnowBlockPrefix = "SM";

    protected String mPrefix;
    protected String mRetId;

    public UniqueStrIdGen(String prefix, int baseUniqueId)
    {
        super(baseUniqueId);

        this.mPrefix = prefix;
    }

    public String genNewStrId()
    {
        this.mRetId = String.format("{0}_{1}", this.mPrefix, this.genNewId());
        return this.mRetId;
    }

    public String getCurStrId()
    {
        return this.mRetId;
    }

    public String genStrIdById(int id)
    {
        this.mRetId = String.format("{0}_{1}", mPrefix, id);
        return this.mRetId;
    }
}