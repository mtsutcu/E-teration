package com.mtsapps.eteration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtsapps.eteration.data.local.entity.FavoriteProduct
import com.mtsapps.eteration.data.repository.FavouriteProductRepositoryImpl
import com.mtsapps.eteration.domain.models.Product
import com.mtsapps.eteration.domain.use_cases.InsertCartEntityUseCase
import com.mtsapps.eteration.presentation.detail.DetailUIEvent
import com.mtsapps.eteration.presentation.detail.DetailUIState
import com.mtsapps.eteration.presentation.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var insertCartEntityUseCase: InsertCartEntityUseCase

    @Mock
    private lateinit var favouriteProductRepository: FavouriteProductRepositoryImpl

    private lateinit var detailViewModel: DetailViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        detailViewModel = DetailViewModel(
            insertCartEntityUseCase,
            favouriteProductRepository
        )
    }

    @Test
    fun `when OnSetProduct event is triggered, product should be set and checked if favorite`() = runTest {
        val product = Product(id = "1", brand = "Test Brand", price = "10.00", image = "test_url", description = "Test Product")
        `when`(product.id?.let { favouriteProductRepository.isFavorite(it) }).thenReturn(flowOf(null))

        detailViewModel.handleEvent(DetailUIEvent.OnSetProduct(product))
        assertEquals(product, detailViewModel.uiState.value.product)
        assertFalse(detailViewModel.uiState.value.isFav)
    }

    @Test
    fun `when OnAddFavourite event is triggered, product should be added to favorites`() = runTest {
        val product = Product(id = "1", brand = "Test Brand", price = "10.00", image = "test_url", description = "Test Product")
        detailViewModel.setState { DetailUIState(product = product) }
        detailViewModel.handleEvent(DetailUIEvent.OnAddFavourite)
        verify(favouriteProductRepository).insert(FavoriteProduct(productId = product.id!!, image = product.image!!, price = product.price!!, name = product.brand!!))
    }

    @Test
    fun `when OnDeleteFavourite event is triggered, product should be removed from favorites`() = runTest {
        val product = Product(id = "1", brand = "Test Brand", price = "10.00", image = "test_url", description = "Test Product")
        detailViewModel.setState { DetailUIState(product = product) }
        detailViewModel.handleEvent(DetailUIEvent.OnDeleteFavourite)
        verify(favouriteProductRepository).delete(product.id.toString())
    }

    @Test
    fun `when OnAddCartEntity event is triggered, product should be added to cart`() = runTest {
        val product = Product(id = "1", brand = "Test Brand", price = "10.00", image = "test_url", description = "Test Product")
        detailViewModel.setState { DetailUIState(product = product) }
        detailViewModel.handleEvent(DetailUIEvent.OnAddCartEntity)}}


