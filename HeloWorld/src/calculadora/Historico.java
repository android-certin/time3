/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bdias
 */
public class Historico {

    private static final String ARQ_HISTORICO = "historico";
    private final File file;
    private static Historico instance;

    private Historico() {
        file = new File(ARQ_HISTORICO);
    }

    public static Historico getInstance() {
        if (null == instance) {
            instance = new Historico();
        }

        return new Historico();
    }

    public boolean writeHistorico(String entrada, String tipoConversao) {
        boolean writed = false;
        FileOutputStream stream;
        try {
            stream = new FileOutputStream(file, true);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(CalculadoraJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String itemHistorico;
            itemHistorico = new StringBuilder()
                    .append(entrada)
                    .append(" (")
                    .append(tipoConversao)
                    .append(" )\n")
                    .toString();
            try {
                stream.write(itemHistorico.getBytes());
                stream.flush();
                stream.close();
                writed = true;
            } catch (IOException ex) {
                Logger.getLogger(CalculadoraJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CalculadoraJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return writed;
    }

    public String readHistorico() {
        String content = "";
        if (file.exists()) {
            byte[] bytes;
            try {
                bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                content = new String(bytes, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                Logger.getLogger(CalculadoraJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return content;
    }

}
