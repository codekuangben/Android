package SDK.Lib.DataStruct;

public class MArray
{
	public static void Copy(void*src, long srcIndex, void*dest, long destIndex, long length)
	{
		char*tmp_source,*tmp_dest;
		tmp_source = (char*)src + srcIndex;
		tmp_dest = (char*)dest + destIndex;

		// 函数memcpy()   从source  指向的区域向dest指向的区域复制count个字符，如果两数组重叠，不定义该函数的行为。而memmove(), 如果两函数重叠，赋值仍正确进行。memcpy函数假设要复制的内存区域不存在重叠，如果你能确保你进行复制操作的的内存区域没有任何重叠，可以直接用memcpy；如果你不能保证是否有重叠，为了确保复制的正确性，你必须用memmove。memcpy的效率会比memmove高一些，如果还不明白的话可以看一些两者的实现： 函数memcpy()   从source  指向的区域向dest指向的区域复制count个字符，如果两数组重叠，不定义该函数的行为。而memmove(), 如果两函数重叠，赋值仍正确进行。	memcpy函数假设要复制的内存区域不存在重叠，如果你能确保你进行复制操作的的内存区域没有任何重叠，可以直接用memcpy；如果你不能保证是否有重叠，为了确保复制的正确性，你必须用memmove。memcpy的效率会比memmove高一些
		// 不用调整从 0 开始存储数据，只要是线性就可以了
		memmove(tmp_dest, tmp_source, length);
	}

	public static void Reverse(char*buff, int index, int length)
	{
		// 如果是 length 0 或者 1 直接返回
		if (length <= 1) {
			return;
		}

		char*tmp_source = buff + index;
		char*tmp_dest = buff + index + (length - 1);
		char tmpChar;

		while (tmp_source < tmp_dest)    // 只有起始地址没有超过目的地址才交换
		{
			tmpChar =*tmp_dest;
			*tmp_dest =*tmp_source;
			*tmp_source = tmpChar;

			++tmp_source;
			--tmp_dest;
		}
	}

	public static void Clear(char*buff, int index, int length)
	{
		memset(buff + index, 0, length);
	}
}