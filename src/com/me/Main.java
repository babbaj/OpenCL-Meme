package com.me;

import org.lwjgl.opencl.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.util.List;

import static org.lwjgl.opencl.CL10.*;

public class Main {

    private static final File code_directory = new File("opencl code");
    private static final File bedrock_file = new File(code_directory, "bedrock.cl");

    // 16x16
    private static final int[] full_pattern = Patterns.PATTERN_FULL_FAR;

    private static final ByteBuffer pattern_buffer = intToByteBuffer(full_pattern);

    private static final int CHUNK_SIZE = 10000;

    public static void main(String[] args) throws Exception {
        // Initialize OpenCL and create context and command queu
        CL.create();
        CLPlatform platform = CLPlatform.getPlatforms().get(0);
        List<CLDevice> devices = platform.getDevices(CL_DEVICE_TYPE_CPU);
        CLContextCallback contextCB = new CLContextCallback() {
            @Override
            protected void handleMessage(String s, ByteBuffer byteBuffer) {
                System.err.println("cl_context_callback info: " + s);
            }
        };
        CLContext context = CLContext.create(platform, devices, contextCB, null, null);
        CLCommandQueue queue = clCreateCommandQueue(context, devices.get(0), CL_QUEUE_PROFILING_ENABLE, null);


        CLMem patternMem = clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR, pattern_buffer, null);
        clEnqueueWriteBuffer(queue, patternMem, 1, 0, pattern_buffer, null, null);
        final String bedrock_source = readFile(bedrock_file);
        CLProgram program = clCreateProgramWithSource(context, bedrock_source, null);
        int err = clBuildProgram(program, devices.get(0), "", null);
        if (err != 0) {
            printBuildLog(program, devices.get(0));
            Util.checkCLError(err);
        }
        CLKernel kernel = clCreateKernel(program, "bedrock_finder_fullpattern", null);

        final int processors = CHUNK_SIZE;
        final int end = 100_000;
        final int numChunks = end/CHUNK_SIZE;
        PointerBuffer bedrockWorkSize = BufferUtils.createPointerBuffer(1);
        bedrockWorkSize.put(0, processors);

        long startTime = System.currentTimeMillis();
        System.out.println("Starting");

        for (int i = 0; i < numChunks; i++) {
            kernel.setArg(0, patternMem); // pattern
            kernel.setArg(1, i * CHUNK_SIZE); // start
            kernel.setArg(2, (i+1) * CHUNK_SIZE); // end
            kernel.setArg(3, processors); // step
            // execute kernel
            clEnqueueNDRangeKernel(queue, kernel, 1, null, bedrockWorkSize, null, null, null);
            clFinish(queue);
            System.out.printf("chunk complete %d/%d\n", i+1, numChunks);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime)/1000D);


        // Clean up OpenCL resources
        clReleaseKernel(kernel);
        clReleaseProgram(program);
        clReleaseMemObject(patternMem);
        clReleaseCommandQueue(queue);
        clReleaseContext(context);
        CL.destroy();
    }

    private static void printBuildLog(CLProgram program, CLDevice device) {
        PointerBuffer size = PointerBuffer.allocateDirect(1); // can only get value once or jvm will crash for some reason
        clGetProgramBuildInfo(program, device, CL_PROGRAM_BUILD_LOG, null, size); // get the length
        ByteBuffer buffer = BufferUtils.createByteBuffer((int)size.get());
        clGetProgramBuildInfo(program, device, CL_PROGRAM_BUILD_LOG, buffer, null); // write to buffer
        print(buffer);
    }

    private static ByteBuffer intToByteBuffer(int[] ints) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(ints.length);
        for (int i = 0; i < ints.length; i++) {
            buffer.put(i, (byte)ints[i]);
        }
        return buffer;
    }


    /** Utility method to convert float array to float buffer
     * @param floats - the float array to convert
     * @return floats_a float buffer containing the input float array
     */
    private static FloatBuffer toFloatBuffer(float... floats) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(floats.length).put(floats);
        buf.rewind();
        return buf;
    }


    /** Utility method to print floats_a float buffer
     * @param buffer - the float buffer to print to System.out
     */
    private static void print(FloatBuffer buffer) {
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.print(buffer.get(i)+" ");
        }
        System.out.println();
    }
    private static void print(ByteBuffer buffer) {
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.print((char)buffer.get(i));
        }
        System.out.println();
    }


    private static void printOutput(ByteBuffer buffer) {
        for (int i = 0; i < buffer.capacity(); i++) {
            final char c = (char)buffer.get(i);
            if (i % 8 == 0) System.out.println();
            if (c != 0) {
                if (c == '?')
                    System.out.print(i/8);
                else
                    System.out.print(c);
            }
        }
        System.out.println();
    }

    private static String readFile(File f) {
        try {
            return new String(Files.readAllBytes(f.toPath()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
