import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.eteration.databinding.ProductLoadStateItemBinding

class ProductLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ProductLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ProductLoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: ProductLoadStateItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.productLoadStateRetryText.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.productLoadStateProgress.isVisible = loadState is LoadState.Loading
            binding.productLoadStateRetryText.isVisible = loadState is LoadState.Error
        }
    }
}
