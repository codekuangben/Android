package SDK.Lib.DataStruct.NoOrPriorityList;

/**
 * @brief 非优先级或者优先级列表
 */
public interface INoOrPriorityList
{
    void setIsSpeedUpFind(boolean value);
    void setIsOpKeepSort(boolean value);
    void Clear();
    int Count();

    INoOrPriorityObject get(int index);
    boolean Contains(INoOrPriorityObject item);
    void RemoveAt(int index);
    int getIndexByNoOrPriorityObject(INoOrPriorityObject priorityObject);

    void addNoOrPriorityObject(INoOrPriorityObject noPriorityObject, float priority);
    void removeNoOrPriorityObject(INoOrPriorityObject noPriorityObject);
}