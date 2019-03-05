//package com.example.qjh.r.UserMessage;
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.qjh.r.R;
//
//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
//    @NonNull
//    @Override
//    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        final View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_message,viewGroup,false);
//        final UserAdapter.ViewHolder viewHolder=new UserAdapter.ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int i) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//
//    /*
//   内部类
//    */
//    static class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView User;
//        View Totall_view;
//        ImageView go;
//        TextView name;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            User=(ImageView)itemView.findViewById(R.id.fruit);
//            name=(TextView)itemView.findViewById(R.id.furitname);
//            go=(ImageView)itemView.findViewById(R.id.fruit);
//            Totall_view=itemView;
//        }
//    }
//}
