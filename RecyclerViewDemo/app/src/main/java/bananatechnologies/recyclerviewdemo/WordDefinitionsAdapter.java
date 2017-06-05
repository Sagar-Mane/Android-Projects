package bananatechnologies.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by inspire on 05-06-2017.
 */

public class WordDefinitionsAdapter extends RecyclerView.Adapter<WordDefinitionsAdapter.MyViewHolder>{

    private List<String> definitions;
    private List<WordProperties> word;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView text, lexical_category, definition;


    public MyViewHolder(View view) {
        super(view);
        text = (TextView) view.findViewById(R.id.text);
        lexical_category = (TextView) view.findViewById(R.id.lexical_category);
        definition = (TextView) view.findViewById(R.id.definition);
    }
}
    public WordDefinitionsAdapter(List<WordProperties> word) {
        this.word = word;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.definitionslist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        WordProperties w=word.get(position);

        holder.text.setText(w.getText());
        holder.lexical_category.setText(w.getLexical_category());
        holder.definition.setText(w.getDefinitions());
    }

    public int getItemCount() {
        return word.size();
    }

}
