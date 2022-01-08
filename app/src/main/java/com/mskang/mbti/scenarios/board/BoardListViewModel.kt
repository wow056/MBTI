package com.mskang.mbti.scenarios.board

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flowOf

class BoardListViewModel: ViewModel(){

    val boardListDataList = flowOf(
        listOf(
            BoardListData(
                "대충 싸웠다는글",
                "대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용",
                "2022.01.08",
                "jfa3ojfwalf"
            ),

            BoardListData(
                "대충 싸웠다는글1",
                "대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용",
                "2022.01.08",
                "jfa3ojfwalf"
            ),

            BoardListData(
                "대충 싸웠다는글2",
                "대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용",
                "2022.01.08",
                "jfa3ojfwalf"
            ),

            BoardListData(
                "대충 싸웠다는글3",
                "대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용",
                "2022.01.08",
                "jfa3ojfwalf"
            ),

            BoardListData(
                "대충 싸웠다는글4",
                "대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용",
                "2022.01.08",
                "jfa3ojfwalf"
            ),

            BoardListData(
                "대충 싸웠다는글5",
                "대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용대충 싸웠다는내용",
                "2022.01.08",
                "jfa3ojfwalf"
            ),
        )
    )
}