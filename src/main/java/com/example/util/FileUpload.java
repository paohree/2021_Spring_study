package com.example.util;


import com.example.board.BoardDAO;
import com.example.board.BoardVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class FileUpload {
    public BoardVO uploadPhoto(HttpServletRequest request){
        String filename = "";
        int sizeLimit = 15 * 4096 * 4096;

        String realPath = request.getServletContext().getRealPath("resource/img");

        File dir = new File(realPath);
        if (!dir.exists()) dir.mkdirs();

        BoardVO one = null;
        MultipartRequest multipartRequest = null;
        try{
            multipartRequest = new MultipartRequest(request, realPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());

            filename = request.getContextPath() + "/img/" + multipartRequest.getFilesystemName("imagefile");
            System.out.println(filename);

            one = new BoardVO();
            String seq = multipartRequest.getParameter("seq");
            if(seq!=null&&!seq.equals("")) one.setSeq(Integer.parseInt(seq));

            // edit이면 아래 무시
            if(multipartRequest.getParameter("pw") != null){
                one.setPw(multipartRequest.getParameter("pw"));
                one.setNickname(multipartRequest.getParameter("nickname"));
                one.setIp(multipartRequest.getParameter("ip"));
            }

            one.setContent(multipartRequest.getParameter("content"));

            if(seq!=null&&!seq.equals("")){
                BoardDAO dao = new BoardDAO();
                String oldfilename = multipartRequest.getParameter("oldfilename");
                if(filename != null && oldfilename != null){
                    System.out.println("changed file to" + filename);
                    FileUpload.deleteFile(request, oldfilename);
                }
                else if (filename == null && oldfilename!=null) {
                    System.out.println("no need to change file name");
                    filename = oldfilename;
                }
            }

            one.setImage(filename);
            System.out.println("good");
        } catch (Exception e){
            e.printStackTrace();
        }
        return one;
    }

    public static void deleteFile(HttpServletRequest request, String filename){
        String filePath = request.getServletContext().getRealPath("resource/img");

        File f = new File(filePath + "/" + filename);
        if( f.exists() ) f.delete();
        else {
            System.out.println("file does not exist");
        }
    }
}
