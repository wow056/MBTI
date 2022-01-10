package com.mskang.mbti.scenarios.board

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mskang.mbti.MBTIGrid
import com.mskang.mbti.MBTIImageGrid
import com.mskang.mbti.R
import com.mskang.mbti.api.model.posts.PostsMBTIItem
import com.mskang.mbti.scenarios.board.detail.PostActivity
import com.mskang.mbti.scenarios.mbti.MBTIDetailActivity
import com.mskang.mbti.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardListActivity : ComponentActivity() {

    private val viewModel by viewModels<BoardListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val boardListDataList by viewModel.mbtiItems.collectAsState(initial = emptyList())
            val navController = rememberNavController()
            MainTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)) {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)) {
                        Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "MBTI 게시판",
                                style = navigationBarTextStyle,
                                modifier = Modifier
                                    .padding(vertical = 14.dp, horizontal = 15.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Image(painter = painterResource(id = R.drawable.ic_chevron_down), contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Image(painter = painterResource(id = R.drawable.ic_write), contentDescription = null,
                            Modifier
                                .padding(end = 28.dp)
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    startActivity(
                                        Intent(
                                            this@BoardListActivity,
                                            WriteActivity::class.java
                                        )
                                    )
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    // A surface container using the 'background' color from the theme
                    Scaffold(
                        bottomBar = {
                            BottomNavigation(backgroundColor = Color.White) {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                BottomNavigationItem(
                                    icon = { Image(painter = painterResource(id = R.drawable.ic_home), contentDescription = null ) },
                                    selected = currentDestination?.hierarchy?.any { it.route == "board" } == true,
                                    onClick = {
                                        navController.navigate("board") {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                                BottomNavigationItem(
                                    icon = { Image(painter = painterResource(id = R.drawable.ic_mbti), contentDescription = null ) },
                                    selected = currentDestination?.hierarchy?.any { it.route == "mbti"  } == true,
                                    onClick = {
                                        navController.navigate("mbti" ) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        NavHost(navController, startDestination = "board", Modifier.padding(innerPadding)) {
                            composable("board") {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp, vertical = 8.dp)
                                        .horizontalScroll(rememberScrollState())
                                        .clickable { navController.navigate("select_mbti") }, verticalAlignment = Alignment.CenterVertically) {
                                        viewModel.mbtiSelection.collectAsState().value.forEach { item ->
                                            Text("#$item", modifier= Modifier
                                                .background(
                                                    shape = CircleShape, color = Color.White
                                                )
                                                .border(
                                                    border = BorderStroke(
                                                        1.dp,
                                                        color = Color(0xFFD2CFCF)
                                                    ), shape = CircleShape
                                                )
                                                .padding(horizontal = 12.dp, vertical = 4.dp))
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                        Text("+", modifier= Modifier
                                            .background(
                                                shape = CircleShape, color = Color.White
                                            )
                                            .border(
                                                border = BorderStroke(
                                                    1.dp,
                                                    color = Color(0xFFD2CFCF)
                                                ), shape = CircleShape
                                            )
                                            .padding(horizontal = 12.dp, vertical = 4.dp))
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .verticalScroll(state = rememberScrollState())
                                    ) {
                                        boardListDataList.forEach { boardListData ->
                                            BoardListItem(postsMBTIItem = boardListData, 256, 256)
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                    }
                                }

                            }

                            composable("select_mbti") {
                                var selectedList by remember{ mutableStateOf(viewModel.mbtiSelection.value) }
                                Column(modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = MaterialTheme.colors.background)
                                ) {

                                    Spacer(modifier = Modifier.height(1.dp))
                                    Column(
                                        modifier = Modifier
                                            .background(color = Color.White)
                                            .fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(24.dp))
                                        Text("관심있는 MBTI를 선택해주세요.", fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text("선택하신 MBTI 관련된 글만 보여집니다.")
                                        Spacer(modifier = Modifier.height(24.dp))
                                        MBTIGrid(selectedItems = selectedList, onSelectItem = { selectedItem ->
                                            selectedList = if (selectedList.contains(selectedItem)) {
                                                selectedList.filterNot { selectedItem == it }
                                            } else {
                                                selectedList + selectedItem
                                            }
                                        })
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(onClick = {
                                            viewModel.mbtiSelection.value = selectedList
                                            navController.popBackStack()
                                        },
                                            modifier = Modifier
                                                .padding(horizontal = 24.dp)
                                                .fillMaxWidth(),
                                            shape = buttonShape,
                                            contentPadding = PaddingValues(vertical = 14.dp)
                                        ) {
                                            Text("필터 적용")
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))
                                    }
                                }
                            }
                            composable("mbti") {
                                MBTIImageGrid(onClickItem = { image ->
                                    startActivity(Intent(this@BoardListActivity, MBTIDetailActivity::class.java)
                                        .putExtra(MBTIDetailActivity.keyMBTIImage, image)
                                    )
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BoardListItem(postsMBTIItem: PostsMBTIItem, likeCount: Int, commentCount: Int) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth()
                .clickable {
                    startActivity(
                        Intent(this, PostActivity::class.java)
                            .putExtra(PostActivity.keyUUID, postsMBTIItem.uuid)
                    )
                }) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = postsMBTIItem.title, style = previewTitleStyle)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = postsMBTIItem.content.toString(), style = previewBodyStyle)
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "MBTI", color = ColorSecondary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = postsMBTIItem.userName.toString(), color = Gray500)
                    Spacer(modifier = Modifier.weight(1f))
                    Image(painter = painterResource(id = R.drawable.ic_heart), contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = likeCount.toString(), color = Gray500)
                    Spacer(modifier = Modifier.width(19.dp))
                    Image(painter = painterResource(id = R.drawable.ic_comment), contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = commentCount.toString(), color = Gray500)

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}