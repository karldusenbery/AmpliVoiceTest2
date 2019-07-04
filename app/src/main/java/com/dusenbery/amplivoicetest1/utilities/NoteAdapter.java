package com.dusenbery.amplivoicetest1.utilities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dusenbery.amplivoicetest1.R;
import com.dusenbery.amplivoicetest1.model.Note;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {

    private AdapterView.OnItemClickListener listener;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {

        // sets the Tite text
        holder.textViewTitle.setText(model.getTitle());

        // gets the creation date string from the document
        String creationDateFromDocument = model.getCreationDate();

        String parsedAndConvertedDate;

        String year;
        String month;
        String day;

        String convertedMonth;
        String convertedDay;

        char day1stChar;
        char day2ndChar;
        String day1stCharAsString;
        String day2ndCharAsString;
        int day1stDigit;


        String delims = "[-]";
        String[] dateParts = creationDateFromDocument.split(delims);

        year = dateParts[0];
        month = dateParts[1];

        //month converting code
        String[] monthsArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        if (month.equals("01")) {
            convertedMonth = monthsArray[0];
        }
        else if (month.equals("02")) {
            convertedMonth = monthsArray[1];
        }
        else if (month.equals("03")) {
            convertedMonth = monthsArray[2];
        }
        else if (month.equals("04")) {
            convertedMonth = monthsArray[3];
        }
        else if (month.equals("05")) {
            convertedMonth = monthsArray[4];
        }
        else if (month.equals("06")) {
            convertedMonth = monthsArray[5];
        }
        else if (month.equals("07")) {
            convertedMonth = monthsArray[6];
        }
        else if (month.equals("08")) {
            convertedMonth = monthsArray[7];
        }
        else if (month.equals("09")) {
            convertedMonth = monthsArray[8];
        }
        else if (month.equals("10")) {
            convertedMonth = monthsArray[9];
        }
        else if (month.equals("11")) {
            convertedMonth = monthsArray[10];
        }
        else if (month.equals("12")) {
            convertedMonth = monthsArray[11];
        }
        else {
            convertedMonth = month;
        }

        //day converting code
        day1stChar = dateParts[2].charAt(0);
        day2ndChar = dateParts[2].charAt(1);
        day1stCharAsString = String.valueOf(day1stChar);
        day2ndCharAsString = String.valueOf(day2ndChar);
        day1stDigit = Character.getNumericValue(day1stChar);
        day = day1stCharAsString + day2ndCharAsString;
        if (day1stDigit == 0){
            convertedDay = day2ndCharAsString;
        }
        else {
            convertedDay = day;
        }


        // concatenates the date stings to one string to set in the TextView
        parsedAndConvertedDate = (convertedDay + " " + convertedMonth + " " + year);

        //sets the date text view
        holder.textViewCreationDate.setText(parsedAndConvertedDate);

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewCreationDate;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewCreationDate = itemView.findViewById(R.id.text_view_creation_date);

            // Sets up the the onClickListener for the note we clicked on
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}