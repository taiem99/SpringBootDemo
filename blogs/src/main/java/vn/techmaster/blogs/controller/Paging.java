package vn.techmaster.blogs.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Paging {
    private String title;
    private int index;
    private String active;

    public Paging(String title, int index, String active){
        this.title = title;
        this.index = index;
        this.active = active;
    }
    
    public static List<Paging> generatePage(int selectedPage, int totalPages){
        int offset = Math.min(5, totalPages);
        int start = selectedPage - (offset / 2);
        start= Math.max(start, 0);
        int end = start + offset;
        if(end > totalPages){
            end = totalPages;
            start = end - offset + 1;
        }
        ArrayList<Paging> pagings = new ArrayList<>();
        pagings.add(new Paging("Prev", selectedPage > 0 ? selectedPage - 1 : 0, ""));
        for(int i = start; i < end ; i++){
            Paging paging = new Paging(String.valueOf(i + 1), i , (i == selectedPage) ? "active" : "");
            pagings.add(paging);
        }
        pagings.add(new Paging("Next", Math.min(selectedPage + 1, totalPages - 1), ""));
        return pagings;
    }
}
