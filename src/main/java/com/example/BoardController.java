package com.example;

import com.example.board.BoardService;
import com.example.board.BoardVO;
import com.example.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;

    //BoardDAO boardDAO;
    @RequestMapping(value = "board/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("list", boardService.getBoardList());
        return "list";
    }

    @RequestMapping(value = "board/add", method = RequestMethod.GET)
    public String addPost(){
        return "addpostform";
    }

    @RequestMapping(value = "board/addok", method = RequestMethod.POST)
    public String addPostOk(HttpServletRequest request, Model model) {
        try{
            BoardVO vo;
            FileUpload upload = new FileUpload();
            vo = upload.uploadPhoto(request);

            int i = boardService.insertBoard(vo);

            if(i == 0){
                System.out.println("데이터 추가 성공!");
            } else {
                System.out.println("데이터 추가 실패.");
            }
        } catch (Exception e) {
            model.addAttribute("errorlog", e.getStackTrace().toString());
            return "errorlog";
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/board/editpost/{id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("id") int id, Model model, ServletRequest request) {
        BoardVO boardVO = boardService.getBoard(id);
        if(request.getParameter("pw").equals(boardVO.getPw())){
            model.addAttribute("boardVO", boardVO);
            return "editpost";
        }
        return "redirect:../list";
    }

    @RequestMapping(value = "/board/editok", method = RequestMethod.POST)
    public String editPostOk(HttpServletRequest request) {
        // vo 로 못 받아온다
        BoardVO vo;
        FileUpload upload = new FileUpload();
        vo = upload.uploadPhoto(request);

        System.out.println("Will update image to :" + vo.getImage());

        int i = boardService.updateBoard(vo);
        if(i == 0){
            System.out.println("데이터 수정 성공!");
        } else {
            System.out.println("데이터 수정 실패.");
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/board/delete/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") int id, HttpServletRequest request) {
        BoardVO boardVO = boardService.getBoard(id);
        if(request.getParameter("pw").equals(boardVO.getPw())){
            String filename = boardService.getBoard(id).getImage();
            int i = boardService.deleteBoard(id);
            if(i == 0){
                FileUpload.deleteFile(request, filename);
                System.out.println("데이터 삭제 성공!");
            } else {

                System.out.println("데이터 삭제 실패.");
            }
        }
        return "redirect:../list";
    }

    @RequestMapping(value = "board/like", method = RequestMethod.GET)
    public String addLove(BoardVO vo, HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("q"));
        int likecount = boardService.getBoard(id).getLikecount();

        vo.setLikecount(likecount+1);
        vo.setSeq(id);
        int i = boardService.updatelikecount(vo);

        model.addAttribute("count", likecount+1);

        if(i == 0){
            System.out.println("like 성공" + (likecount+1));
        } else {
            System.out.println("like 실패.");
        }
        return "likecount";
    }
}
