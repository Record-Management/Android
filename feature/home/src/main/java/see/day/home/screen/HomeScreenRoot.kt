package see.day.home.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.snap
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray30
import see.day.home.R
import see.day.home.component.CalendarDetail
import see.day.home.component.HomeImage
import see.day.home.component.HomeTopBar
import see.day.home.component.SelectedDateComponent
import see.day.home.component.SelectedFilterRecordType
import see.day.home.state.HomeUiEffect
import see.day.home.state.HomeUiEvent
import see.day.home.state.HomeUiState
import see.day.home.util.RecordFilterType
import see.day.home.util.rememberNavigationBarHeight
import see.day.home.viewModel.HomeViewModel
import see.day.model.record.RecordType
import see.day.ui.calendar.CustomCalendar

@Composable
fun HomeScreenRoot(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel(), isRefresh: Boolean, onClickAddRecord: (RecordType) -> Unit, onClickDetailRecord: (RecordType, String) -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        snapshotFlow { isRefresh }
            .collect { refresh ->
                if(refresh) {
                    viewModel.onEvent(HomeUiEvent.OnRefresh)
                }
            }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeUiEffect.OnGoAddRecord -> {
                    onClickAddRecord(effect.recordType)
                }

                is HomeUiEffect.OnGoDetailRecord -> {
                    onClickDetailRecord(effect.recordType, effect.recordId)
                }
            }
        }
    }

    HomeScreen(
        modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, uiState: HomeUiState, uiEvent: (HomeUiEvent) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberStandardBottomSheetState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState)
    val bottomSheetContentScroll = rememberScrollState()
    val configuration = LocalConfiguration.current
    val bottomSheetMinHeight by remember(configuration) { mutableStateOf((configuration.screenHeightDp * 0.6f).dp) }
    val statusBarPadding = WindowInsets.statusBars
        .asPaddingValues()
    val topPaddingFraction = calculateTopPaddingFraction(configuration.screenHeightDp.dp, statusBarPadding.calculateTopPadding())

    val navigationBarSize = rememberNavigationBarHeight()

    var bottomSheetStateMinOffset by remember { mutableStateOf<Float?>(null) }
    var bottomSheetStateMaxOffset by remember { mutableStateOf<Float?>(null) }
    var topAppBarAlpha by remember { mutableStateOf(0f) }
    var floatingButtonPadding by remember { mutableStateOf(70f + navigationBarSize.value) }

    val onDownBottomSheet: () -> Unit = {
        coroutineScope.launch {
            bottomSheetState.partialExpand()
        }
    }

    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.requireOffset() }
            .collect { offset ->
                // 최초 수집 시 최소/최대 값 기억
                if (bottomSheetStateMinOffset == null || offset < bottomSheetStateMinOffset!!) bottomSheetStateMinOffset = offset
                if (bottomSheetStateMaxOffset == null || offset > bottomSheetStateMaxOffset!!) bottomSheetStateMaxOffset = offset

                val min = bottomSheetStateMinOffset
                val max = bottomSheetStateMaxOffset
                if (min != null && max != null) {
                    // 0f ~ 1f 사이로 정규화해서 알파 계산
                    topAppBarAlpha = 1f - ((offset - min) / (max - min)).coerceIn(0f, 1f)

                    floatingButtonPadding = (70f + navigationBarSize.value) * ((offset - min) / (max - min)).coerceIn(0f, 1f)
                }
            }
    }

    LaunchedEffect(bottomSheetState.currentValue) {
        if (bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
            if (bottomSheetContentScroll.value != 0) {
                bottomSheetContentScroll.animateScrollTo(0)
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HomeImage(modifier)
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            topBar = {
                HomeTopBar(
                    modifier = modifier,
                    alpha = topAppBarAlpha,
                    isFullExpand = bottomSheetState.currentValue == SheetValue.Expanded,
                    onClickBackButton = {
                        onDownBottomSheet()
                    }
                )
            },
            sheetContent = {
                HomeBottomSheetContent(
                    modifier,
                    topPaddingFraction,
                    bottomSheetContentScroll,
                    bottomSheetState,
                    uiState,
                    uiEvent,
                    floatingButtonPadding
                )
            },
            sheetPeekHeight = bottomSheetMinHeight,
            sheetShape = if (bottomSheetState.currentValue == SheetValue.Expanded) {
                RoundedCornerShape(0.dp)
            } else {
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            },
            sheetContainerColor = Color.White
        ) { innerPadding ->
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeBottomSheetContent(
    modifier: Modifier,
    topPaddingFraction: Float,
    bottomSheetContentScroll: ScrollState,
    bottomSheetState: SheetState,
    uiState: HomeUiState,
    uiEvent: (HomeUiEvent) -> Unit,
    floatingButtonPadding: Float
) {
    Column(
        modifier = modifier
            .fillMaxHeight(fraction = topPaddingFraction)
            .fillMaxWidth()
            .verticalScroll(bottomSheetContentScroll, bottomSheetState.currentValue == SheetValue.Expanded)
    ) {
        Row(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SelectedDateComponent(modifier, uiState.currentYear, uiState.currentMonth, uiEvent)
            Spacer(modifier = modifier.weight(1f))
            SelectedFilterRecordType(modifier, uiState.selectedFilterType, uiEvent)
        }
        Spacer(modifier = modifier.padding(top = 10.dp))
        Box {
            CustomCalendar(
                modifier = modifier,
                currentYear = uiState.currentYear,
                currentMonth = uiState.currentMonth,
                selectedMonth = uiState.selectedMonth,
                selectedDay = uiState.selectedDay,
                calendarDayInfo = uiState.monthlyRecords,
                currentFilterType = uiState.selectedFilterType.toRecordType(),
                mainRecordType = uiState.mainRecordType,
                onClickCell = { year, month, day ->
                    uiEvent(HomeUiEvent.OnClickCell(year, month, day))
                },
                onSwipeCalendar = { year, month ->
                    uiEvent(HomeUiEvent.OnClickSelectedDate(year, month))
                }
            )
            FloatingActionButton(
                onClick = { uiEvent(HomeUiEvent.OnClickAddButton(RecordType.DAILY)) },
                modifier = modifier
                    .padding(
                        end = 16.dp, bottom = floatingButtonPadding.dp
                    )
                    .align(Alignment.BottomEnd),
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.image_edit),
                    contentDescription = "추가하기 버튼",
                    modifier = modifier.size(24.dp)
                )
            }
        }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
                .height(1.dp)
                .background(gray30)
        )
        if (uiState.dailyDetailRecords.records.isNotEmpty()) {
            CalendarDetail(
                dailyDetailRecord = uiState.dailyDetailRecords,
                onClickOverview = { recordType, recordId ->
                    uiEvent(HomeUiEvent.OnClickDetailButton(recordType, recordId))
                }
            )
            Spacer(modifier = modifier.systemBarsPadding())
        }
    }
}

fun calculateTopPaddingFraction(screenHeight: Dp, appTopBarHeight: Dp): Float {
    return (screenHeight - appTopBarHeight - topBarHeight) / screenHeight
}

fun RecordFilterType.toRecordType(): RecordType? {
    return when (this) {
        RecordFilterType.ALL -> null
        RecordFilterType.DAILY -> RecordType.DAILY
        RecordFilterType.EXERCISE -> RecordType.EXERCISE
        RecordFilterType.SCHEDULE -> RecordType.SCHEDULE
        RecordFilterType.HABIT -> RecordType.HABIT
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    SeeDayTheme {
        HomeScreen(
            uiState = HomeUiState.init,
            uiEvent = { }
        )
    }
}

val topBarHeight = 56.dp
