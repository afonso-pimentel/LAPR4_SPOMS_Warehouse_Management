package helpers;

import javassist.bytecode.stackmap.TypeData;
import models.SpomsPacket;

import java.io.*;

public class SpomsPacketTracerImpl implements SpomsPacketTracer{

    private final String fileName;
    private final Class originClass;

    public SpomsPacketTracerImpl(String fileName, Class mainClass){
        this.fileName = fileName;
        this.originClass = mainClass;
    }

    @Override
    public void trace(SpomsPacket packet) {
        try{
            String path = "./" + fileName + ".spoms";

            File file = new File(path);

            if (!file.exists()) {
                System.out.println("Created SPOMS packet tracing file at location: " + path);
                file.createNewFile();
            }

            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true), "UTF-8"));

            writer.append("\n");
            writer.append(packet.toString());
            writer.append("\n");

            writer.close();
        }
        catch(Exception e){
            System.out.println("Error creating SPOMS Packet tracing: " + e.getMessage());
            // SUPRESS ANY ERROR
        }
    }
}
