package africa.semicolon.playlist.playlist.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {

    private Long totalElements;

    private Long totalPages;

    private Long pageNumber;

    private Long pageSize;

    private List<T> content;

}
