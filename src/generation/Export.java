import java.io.*;

public class Export {
    static void exportNetwork (NetWork map, String fileName){
        ObjectOutputStream oout= null;
        try {
            FileOutputStream file = new FileOutputStream(fileName);//on ouvre le fichier
            oout = new ObjectOutputStream(file);//on cree l'objet qu'on va mettre dans le stream

            oout.writeObject(map);
        } catch (final IOException e){//on attrape l'erreur possible avec le flux et le fichier
            e.printStackTrace();
        } finally {
            try {
                if(oout != null) {
                    oout.flush();
                    oout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static NetWork importNetwork (String fileName){
        ObjectInputStream ois = null;
        try{
            FileInputStream file = new FileInputStream(fileName);
            ois = new ObjectInputStream(file);

            NetWork tmpCt = (NetWork) ois.readObject();

            return tmpCt;
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
