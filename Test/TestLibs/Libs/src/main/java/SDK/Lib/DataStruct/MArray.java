package SDK.Lib.DataStruct;

public class MArray
{
	static public void Copy(byte[] src, int srcIndex, byte[] dest, int destIndex, int length)
	{
		MArray.Copy(src, (long)srcIndex, dest, (long)destIndex, (long)length);
	}

	static public void Copy(byte[] src, long srcIndex, byte[] dest, long destIndex, long length)
	{
		int idx = 0;

		while(idx < length)
		{
			dest[(int)(idx + destIndex)] = src[(int)(idx + srcIndex)];

			++idx;
		}
	}

	static public <T> void Copy(T[] src, long srcIndex, T[] dest, long destIndex, long length)
	{
		int idx = 0;

		while(idx < length)
		{
			dest[(int)(idx + destIndex)] = src[(int)(idx + srcIndex)];

			++idx;
		}
	}

	static public void Reverse(byte[] buff)
	{
		MArray.Reverse(buff, 0, buff.length);
	}

	static public void Reverse(byte[] buff, int index)
	{
		MArray.Reverse(buff, index, buff.length);
	}

	static public void Reverse(byte[] buff, int index, int length)
	{
		// 如果是 length 0 或者 1 直接返回
		if (length <= 1) {
			return;
		}

		int tmp_source = index;
		int tmp_dest = index + (length - 1);
		byte tmpChar;

		while (tmp_source < tmp_dest)    // 只有起始地址没有超过目的地址才交换
		{
			tmpChar = buff[tmp_dest];
			buff[tmp_dest] = buff[tmp_source];
			buff[tmp_source] = tmpChar;

			++tmp_source;
			--tmp_dest;
		}
	}

	static public <T> void Reverse(T[] buff, int index, int length)
	{
		// 如果是 length 0 或者 1 直接返回
		if (length <= 1) {
			return;
		}

		int tmp_source = index;
		int tmp_dest = index + (length - 1);
		T tmpChar;

		while (tmp_source < tmp_dest)    // 只有起始地址没有超过目的地址才交换
		{
			tmpChar = buff[tmp_dest];
			buff[tmp_dest] = buff[tmp_source];
			buff[tmp_source] = tmpChar;

			++tmp_source;
			--tmp_dest;
		}
	}

	static public void Clear(byte[] buff, int index, int length)
	{
		int idx = index;

		while(idx < length)
		{
			buff[idx] = 0;

			++idx;
		}
	}

	static public <T> void Clear(T[] buff, int index, int length)
	{
		int idx = index;

		while(idx < length)
		{
			buff[idx] = null;

			++idx;
		}
	}

	static public byte[] getSubBytes(byte[] srcBytes, int begin, int count)
	{
		if(0 == begin && srcBytes.length == count)
		{
			return srcBytes;
		}
		else
		{
			byte[] subBytes = new byte[count];

			for (int i = begin; i < begin + count; i++)
			{
				subBytes[i - begin] = srcBytes[i];
			}

			return subBytes;
		}
	}
}