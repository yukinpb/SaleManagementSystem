import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemInstallmentCustomerBinding
import com.example.fe.interfaces.OnCustomerClickListener
import com.example.fe.model.Customer

class InstallmentCustomerAdapter(
    private var customers: MutableList<Customer>,
    private val itemClickListener: OnCustomerClickListener
) : RecyclerView.Adapter<InstallmentCustomerAdapter.InstallmentCustomerViewHolder>() {

    fun setData(customers: MutableList<Customer>) {
        this.customers = customers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentCustomerViewHolder {
        val binding = ItemInstallmentCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstallmentCustomerViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: InstallmentCustomerViewHolder, position: Int) {
        holder.bindView(customers[position])
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    class InstallmentCustomerViewHolder(
        private val binding: ItemInstallmentCustomerBinding,
        private val itemClickListener: OnCustomerClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(customer: Customer) {
            binding.txtInstallmentCustomerName.text = customer.name
            binding.txtInstallmentCustomerJob.text = "Job: ${customer.job}"
            binding.txtInstallmentCustomerPhoneNumber.text = "Phone number: ${customer.phoneNumber}"

            itemView.setOnClickListener {
                itemClickListener.onItemClick(customer)
            }
        }
    }
}