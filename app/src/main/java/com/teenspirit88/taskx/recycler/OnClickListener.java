package com.teenspirit88.taskx.recycler;


import com.teenspirit88.taskx.entity.Order;

//Интерфейс для обработки клика по элеметну RecyclerView
public interface OnClickListener {
    void onItemClick(Order order);
}
