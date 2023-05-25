package br.unitins.projeto.service.file;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String salvarImagemUsuario(byte[] imagem, String nomeImagem, Long id) throws IOException;
    
    File download(String nomeArquivo); 
}
