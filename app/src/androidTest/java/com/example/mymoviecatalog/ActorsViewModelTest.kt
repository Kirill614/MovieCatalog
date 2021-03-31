package com.example.mymoviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mymoviecatalog.Repository.MainRepository
import com.example.mymoviecatalog.Utils.CoroutineContextProvieder
import com.example.mymoviecatalog.data.ActorDetailsModel
import com.example.mymoviecatalog.data.Person
import com.example.mymoviecatalog.data.PersonModel
import com.example.mymoviecatalog.viewModel.ActorsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.collections.ArrayList

@RunWith(MockitoJUnitRunner::class)
class ActorsViewModelTest {
    private lateinit var viewModel: ActorsViewModel
    @Mock
    private lateinit var viewStateObserver: Observer<ActorsViewModel.ViewModelViewState>
    @Mock
    private lateinit var mainRep: MainRepository
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRep = Mockito.mock(MainRepository::class.java)
        viewModel = ActorsViewModel(mainRep, CoroutineContextProvieder())
        GlobalScope.launch { viewModel.actorsLiveData.observeForever(viewStateObserver) }
    }

    @ExperimentalCoroutinesApi

    @Test
    fun shouldSuccessWhenMainRepReturnsData() {
        testCoroutineRule.runBlocking {
            val data = mainRep.getPersons()
            Mockito.`when`(mainRep.getPersons()).thenReturn(
                PersonModel(
                    ArrayList<Person>()
                )
            )

            viewModel.getPersons()

            Mockito.verify(viewStateObserver).onChanged(ActorsViewModel.ViewModelViewState.Loading)
            // Mockito.verify(viewStateObserver).onChanged(ActorsViewModel.ViewModelViewState.SuccessActors(data))
        }
    }

    @Test
    fun shouldSuccessWhenMainRepReturnsActorDetails() {
        testCoroutineRule.runBlocking {
            Mockito.`when`(mainRep.getPersonDetails(28782))
                .thenReturn(
                    ActorDetailsModel(
                        "",
                        "",
                        ""
                    )
                )

            viewModel.getActorDetails(28782)

            Mockito.verify(viewStateObserver).onChanged(ActorsViewModel.ViewModelViewState.Loading)
            //Mockito.verify(viewStateObserver)
              //  .onChanged(ActorsViewModel.ViewModelViewState.SuccessActorDetails(any()))
        }
    }

}

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val coroutineScope = TestCoroutineScope()
    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(coroutineDispatcher)
            base?.evaluate()
            Dispatchers.resetMain()
            coroutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlocking(block: suspend TestCoroutineScope.() -> Unit) =
        coroutineScope.runBlockingTest { block() }
}