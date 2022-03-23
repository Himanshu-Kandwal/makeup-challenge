package md.absa.makeup.challenge.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import md.absa.makeup.challenge.TestCoroutineRule
import md.absa.makeup.challenge.data.api.resource.NetworkResource
import md.absa.makeup.challenge.data.repository.MakeUpRepositoryImpl
import md.absa.makeup.challenge.model.MakeUpItem
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MakeUpViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryImpl: MakeUpRepositoryImpl

    @Mock
    private lateinit var apiUsersObserver: Observer<NetworkResource<List<MakeUpItem?>>>

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
}
