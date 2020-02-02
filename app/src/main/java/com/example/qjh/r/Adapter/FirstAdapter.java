package com.example.qjh.r.Adapter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.qjh.r.R;
import com.example.qjh.r.Receiver.VPM;
import com.example.qjh.r.Bean.MessageBomb;
import com.google.android.material.snackbar.Snackbar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.ViewHolder> {

    private final Context context;
    private List<MessageBomb> message_bombs;
    private OnItemClickListener onItemClickListener;


    @Override
    public FirstAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.common_item_repairmsg, viewGroup, false);
        final FirstAdapter.ViewHolder viewHolder = new FirstAdapter.ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                MessageBomb message_bomb = (MessageBomb) message_bombs.get(position);
                onItemClickListener.onClick(position);

            }
        });
        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(view,"是否删除数据",Snackbar.LENGTH_SHORT).setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAdapterPosition();
                        MessageBomb message_bombss=new MessageBomb();
                        message_bombss.setObjectId(message_bombs.get(position).getSelf());
                        message_bombss.delete(new UpdateListener() {



                            @Override
                            public void done(BmobException e) {
                                if(e==null)
                                {
                                    Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                        removeItem(position);
                        VPM.SetBadgeNum(message_bombs.size());
                    }
                }).show();


                return true;
            }
        });

        return viewHolder;
    }

    public FirstAdapter(List<MessageBomb> message_bombs, Context context) {
        this.message_bombs = message_bombs;
        this.context=context;
    }

    public void removeItem(int pos) {
        message_bombs.remove(pos);
        notifyItemRemoved(pos);
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    @Override
    public void onBindViewHolder(final FirstAdapter.ViewHolder viewHolder, int i) {
        final MessageBomb msg = message_bombs.get(i);
        viewHolder.Title_Front.setText(msg.getTitle());
        viewHolder.Obj_Front.setText(msg.getObj_Name());
//        viewHolder.number_Front.setText(msg.getNumber());
//        viewHolder.name_Front.setText(msg.getName());
        viewHolder.time_Front.setText(msg.getTime());
        viewHolder.locaton_Front.setText(msg.getLocation());

    //    File file=downloadFile(msg.getPicture());
        Glide.with(viewHolder.view.getContext()).load(msg.getPicture_uri()).into(viewHolder.picture);
        viewHolder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击",Toast.LENGTH_SHORT).show();
                new XPopup.Builder(context)
                        .asImageViewer(viewHolder.picture, msg.getPicture_uri() , true, -1, -1, 50, false,new ImageLoader())
                        .show();

            }
        });



    }

    private File downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {
            @Override
            public void onStart() {
              //  Toast.makeText(getContext(), "开始下载", Toast.LENGTH_SHORT).show();
                Log.i("bmob","开始下载：");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    Log.i("bmob","开始下载："+savePath);
                  //  Toast.makeText(getContext(),"下载成功,保存路径:"+savePath ,Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","开始下载："+e.getErrorCode()+e.getMessage());
                  //  Toast.makeText(getContext(),"下载失败："+e.getErrorCode()+","+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
        return  saveFile;
    }
    public void addData(int position) {

    }

    @Override
    public int getItemCount() {
        return message_bombs.size();
    }


    /*
    内部类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title_Front; //标题
        TextView Obj_Front;  //项目名称
//        TextView number_Front; //学号
//        TextView name_Front; //姓名
        TextView time_Front; //预约时间
        TextView locaton_Front; //位置
        ImageView picture; //图片
        View view;

        public ViewHolder( View itemView) {
            super(itemView);
            Title_Front = (TextView) itemView.findViewById(R.id.title_front);
            Obj_Front = (TextView) itemView.findViewById(R.id.obj_front);
//            number_Front = (TextView) itemView.findViewById(R.id.number_front);
//            name_Front = (TextView) itemView.findViewById(R.id.name_front);
            time_Front = (TextView) itemView.findViewById(R.id.time_front);
            locaton_Front = (TextView) itemView.findViewById(R.id.Location_front);
            picture=(ImageView)itemView.findViewById(R.id.image_front);
            view = itemView;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position,  Object url,  ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round).
                    override(Target.SIZE_ORIGINAL)).into(imageView);
        }

        @Override
        public File getImageFile( Context context,  Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
