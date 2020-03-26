package EntityClasses;

import java.util.ArrayList;
/**
 * Title: BasicOperation.java
 * Description: this class a interface of the basic read, write and delete operation of files.
 * @author Jie Ji
 * @version 1.0
 */
public interface BasicOperation {
   // public void fileWriter();

    /**
     * this method is used to read info from the file
     * @return the info that read from file
     */
    public Object fileReader();

    /**
     * this method is used to over write all info in the file
     * @param O the info we want to write
     */
    public void WriteFiles(Object O);

    /**
     * this method is used to append info in the file
     * @param O the info we want to write
     */
    public void WriteFiles_append(Object O);

    /**
     * this method is used to delete the info
     * @param id
     */
    public void Delete(String id);

  }
