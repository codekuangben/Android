package SDK.Lib.NetWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @brief NetPing
 */

public class NetPing
{
    public static boolean ping(String host, int pingCount, StringBuffer stringBuffer)
    {
        String line = null;
        Process process = null;
        BufferedReader successReader = null;

        // String command = "ping -c " + pingCount + " -w 5 " + host;
        String command = "ping -c " + pingCount + " " + host;
        boolean isSuccess = false;

        try
        {
            process = Runtime.getRuntime().exec(command);

            if (process == null)
            {
                append(stringBuffer, "ping fail:process is null.");
                return false;
            }

            successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = successReader.readLine()) != null)
            {
                append(stringBuffer, line);
            }

            int status = process.waitFor();

            if (status == 0)
            {
                append(stringBuffer, "exec cmd success:" + command);
                isSuccess = true;
            }
            else
            {
                append(stringBuffer, "exec cmd fail.");
                isSuccess = false;
            }

            append(stringBuffer, "exec finished.");
        }
        catch (IOException e)
        {

        }
        catch (InterruptedException e)
        {

        }
        finally
        {
            if (process != null)
            {
                process.destroy();
            }
            if (successReader != null)
            {
                try
                {
                    successReader.close();
                }
                catch (IOException e)
                {

                }
            }
        }

        return isSuccess;
    }

    private static void append(StringBuffer stringBuffer, String text)
    {
        if (stringBuffer != null)
        {
            stringBuffer.append(text + "\n");
        }
    }
}