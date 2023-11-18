package com.food.winterfoodies2.dto.StoreInfo;

import com.food.winterfoodies2.dto.MyPage.OrderListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoReviewApiResponseSuccessDto {
    private String status;
    private List<ReviewDto> data; // ReviewDto 리스트로 변경
    private boolean favorite; // 상점에 대한 찜 여부
}
