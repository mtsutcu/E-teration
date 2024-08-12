
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtsapps.eteration.data.local.entity.CartEntity
import com.mtsapps.eteration.domain.use_cases.CompleteCartUseCase
import com.mtsapps.eteration.domain.use_cases.DeleteCartEntityUseCase
import com.mtsapps.eteration.domain.use_cases.GetAllCartEnititiesUseCase
import com.mtsapps.eteration.domain.use_cases.UpdateCartEntityUseCase
import com.mtsapps.eteration.presentation.cart.CartUIEffect
import com.mtsapps.eteration.presentation.cart.CartUIEvent
import com.mtsapps.eteration.presentation.cart.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getAllCartEntitiesUseCase: GetAllCartEnititiesUseCase

    @Mock
    private lateinit var updateCartEntityUseCase: UpdateCartEntityUseCase

    @Mock
    private lateinit var deleteCartEntityUseCase: DeleteCartEntityUseCase

    @Mock
    private lateinit var completeCartUseCase: CompleteCartUseCase

    private lateinit var viewModel: CartViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = CartViewModel(
            getAllCartEntitiesUseCase,
            updateCartEntityUseCase,
            deleteCartEntityUseCase,
            completeCartUseCase
        )
    }

    @Test
    fun `OnGetAllCarts should get all carts and calculate total price`() = runTest {
        // Arrange
        val cartEntity1 = CartEntity(1, 1, "Pro 1", "100", count = 1)
        val cartEntity2 = CartEntity(2, 2, "Pro 2", "200", count = 2)
        val cartList = listOf(cartEntity1, cartEntity2)
        `when`(getAllCartEntitiesUseCase()).thenReturn(flowOf(cartList))
        viewModel.setEvent(CartUIEvent.OnGetAllCarts)

        advanceUntilIdle()
        assert(viewModel.uiState.value.cartList == cartList)
        assert(viewModel.uiState.value.totalPrice == 500)
    }

    @Test
    fun `OnAddCartEntity should update cart entity`() = runTest {
        val cartEntity = CartEntity(1, 1, "Pro 1", "100", count = 1)
        viewModel.handleEvent(CartUIEvent.OnAddCartEntity(cartEntity))
        advanceUntilIdle()
        verify(updateCartEntityUseCase).invoke(cartEntity)
    }

    @Test
    fun `OnCompleteCart should complete the cart and show snackbar`() = runTest {
        viewModel.handleEvent(CartUIEvent.OnCompleteCart)
        advanceUntilIdle()
        verify(completeCartUseCase).invoke()
        assert(viewModel.effect.first() is CartUIEffect.ShowSnackBar)
    }

    @Test
    fun `OnMinusCartCount should decrease count if more than 1 or delete cart entity`() = runTest {
        val cartEntity = CartEntity(1, 1, "Pro 1", "100", count = 2)
        viewModel.handleEvent(CartUIEvent.OnMinusCartCount(cartEntity))
        advanceUntilIdle()
        verify(updateCartEntityUseCase).invoke(cartEntity.copy(count = 1))
    }

    @Test
    fun `OnMinusCartCount should delete cart entity if count is 1`() = runTest {
        val cartEntity = CartEntity(1, 1, "Pro 1", "100", count = 1)
        viewModel.handleEvent(CartUIEvent.OnMinusCartCount(cartEntity))
        advanceUntilIdle()
        verify(deleteCartEntityUseCase).invoke(cartEntity)
    }

    @Test
    fun `OnPlusCartCount should increase the count`() = runTest {
        val cartEntity = CartEntity(1, 1, "Pro 1", "100", count = 1)
        viewModel.handleEvent(CartUIEvent.OnPlusCartCount(cartEntity))
        advanceUntilIdle()
        verify(updateCartEntityUseCase).invoke(cartEntity.copy(count = 2))
    }
}
