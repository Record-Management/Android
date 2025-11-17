package see.day.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import see.day.domain.repository.GoalRepository
import see.day.model.goal.TreeStage
import see.day.model.record.RecordType
import see.day.network.GoalService
import see.day.network.dto.CommonResponse
import see.day.network.dto.goal.CurrentPeriodGoalResponse
import see.day.network.dto.goal.GoalHistoryResponse
import see.day.network.dto.goal.GoalReportResponse
import see.day.repository.GoalRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class GoalRepositoryTest {

    private lateinit var sut: GoalRepository

    @Mock
    private lateinit var goalService: GoalService

    @Before
    fun setUp() {
        sut = GoalRepositoryImpl(goalService)
    }

    @Test
    fun given_whenHasCurrentPeriodGoal_thenReturnsRecentGoalReport() {
        runTest {
            // given
            val currentPeriodGoalId = "123"
            val currentPeriodGoalResponse = CurrentPeriodGoalResponse(currentPeriodGoalId, RecordType.HABIT, 10, "", "", 0, 0.0, TreeStage.STAGE_1, true)
            val recentHistory: List<GoalHistoryResponse> = listOf()
            val cumulativeAchievementCount = 3

            whenever(goalService.getGoalReport()).thenReturn(
                CommonResponse(
                    200,
                    "S200",
                    "목표 달성 보고서 조회가 성공적으로 완료되었습니다",
                    data = GoalReportResponse(
                        currentPeriod = currentPeriodGoalResponse,
                        cumulativeAchievementCount = cumulativeAchievementCount,
                        recentHistory = recentHistory
                    )
                )
            )

            // when
            val result = sut.getRecentGoalReport().getOrThrow()

            // then
            assertEquals(currentPeriodGoalId, result.goalId)
            assertFalse(result.isGoalCompleted)
        }
    }

    @Test
    fun given_whenHasNoCurrentPeriodGoal_thenReturnsRecentHistory() {
        runTest {
            // given
            val currentHistoryGoalId = "123"
            val currentPeriodGoalResponse : CurrentPeriodGoalResponse? = null
            val recentHistory: List<GoalHistoryResponse> = listOf(
                GoalHistoryResponse(
                    goalId = currentHistoryGoalId,
                    RecordType.HABIT,
                    goalDays = 10,
                    startDate = "",
                    endDate = "",
                    completedDays = 3,
                    achievementRate = 3.0,
                    finalTreeStage = TreeStage.STAGE_1,
                    status = "완료",
                )
            )
            val cumulativeAchievementCount = 3

            whenever(goalService.getGoalReport()).thenReturn(
                CommonResponse(
                    200,
                    "S200",
                    "목표 달성 보고서 조회가 성공적으로 완료되었습니다",
                    data = GoalReportResponse(
                        currentPeriod = currentPeriodGoalResponse,
                        cumulativeAchievementCount = cumulativeAchievementCount,
                        recentHistory = recentHistory
                    )
                )
            )

            // when
            val result = sut.getRecentGoalReport().getOrThrow()

            // then
            assertEquals(currentHistoryGoalId, result.goalId)
            assertTrue(result.isGoalCompleted)
        }
    }
}
