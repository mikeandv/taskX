package com.teenspirit88.taskx.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.teenspirit88.taskx.R;
import com.teenspirit88.taskx.entity.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ListViewHolder> {
    private List<Order> orderList = new ArrayList<>();
    private OnClickListener onClickListener;
    private static final String FORMAT="yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String DAY_MONTH_FORMAT = "d MMMM";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public ListDataAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {

        listViewHolder.bind(orderList.get(i));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if(isPositionHeader(position)) {
//            return TYPE_HEADER;
//        }
//        return TYPE_ITEM;
//    }

//    private boolean isPositionHeader(int position) {
//        return position == 0;
//    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView endAddressTxtField;
        private TextView startAddressTxtField;
        private TextView orderPriceTxtField;
        private TextView orderDateTxtField;

        public ListViewHolder(View itemView) {
            super(itemView);
            endAddressTxtField = itemView.findViewById(R.id.end_addr);
            startAddressTxtField = itemView.findViewById(R.id.start_addr);
            orderPriceTxtField = itemView.findViewById(R.id.order_price);
            orderDateTxtField = itemView.findViewById(R.id.order_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Order order = orderList.get(getLayoutPosition());
                    onClickListener.onItemClick(order);
                }
            });
        }

        public void bind(Order order) {
            endAddressTxtField.setText(order.getEndAddress().getAddress());
            startAddressTxtField.setText(order.getStartAddress().getAddress());
            orderPriceTxtField.setText(order.getprice().getAmountAndSymbol());
            orderDateTxtField.setText(getFormatedDate(order.getOrderTime()));
        }

        private String getFormatedDate(String rDate) {
            SimpleDateFormat utcForm = new SimpleDateFormat(FORMAT, Locale.ROOT);
            SimpleDateFormat appForm = new SimpleDateFormat(DAY_MONTH_FORMAT, Locale.getDefault());

            try{
                Date date = utcForm.parse(rDate);
                return appForm.format(date);
            }
            catch (ParseException e) {
                // TODO: 12/04/2019 добавить логирование или вывод ошибки
                return null;
            }
        }
    }

    public void setItems(List<Order> orders) {
        for(int i = 0; i < 5; i++) {
            orderList.addAll(orders);
        }
        notifyDataSetChanged();
    }

    public void clearItems() {
        orderList.clear();
        notifyDataSetChanged();
    }
}
