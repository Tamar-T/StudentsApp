import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.Student

class StudentsAdapter(
    private val students: List<Student>,
    private val onClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val studentName: TextView = view.findViewById(R.id.studentNameTextView)
        val studentId: TextView = view.findViewById(R.id.studentIdTextView)
        val studentThumbnail: ImageView = view.findViewById(R.id.studentThumbnail)
        val studentCheckBox: CheckBox = view.findViewById(R.id.studentCheckBox)

        fun bind(student: Student) {
            studentName.text = student.name
            studentId.text = "${student.id}"
            studentThumbnail.setImageResource(R.drawable.student_default)
            studentCheckBox.isChecked = student.isChecked

            studentCheckBox.setOnCheckedChangeListener { _, isChecked ->
                student.isChecked = isChecked
            }

            itemView.setOnClickListener {
                onClick(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount() = students.size
}
