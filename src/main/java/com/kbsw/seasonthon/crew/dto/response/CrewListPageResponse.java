package com.kbsw.seasonthon.crew.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CrewListPageResponse {
    private List<CrewListResponse> crews;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private int size;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
}




<<<<<<< HEAD

=======
>>>>>>> e04b319b397ad1c42354c7007afb3daa7e0bd33b
