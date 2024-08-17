package com.example.app_creat_profesionell_cv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterOfLettreDeMotivation extends RecyclerView.Adapter<AdapterOfLettreDeMotivation.ViewHolder> {
    ArrayList<String> arrayList;
    Context context;

    public AdapterOfLettreDeMotivation(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_interview_question,parent,false));
    }

    /*arrayList.add("Acadimic Advisor");
        arrayList.add("Acadimic Coach");
        arrayList.add("Acadimic Coordinator");
        arrayList.add("Acadimic Tutor");
        arrayList.add("Account Executive");
        arrayList.add("Account Manager");
        arrayList.add("Accountant ");
        arrayList.add("Accounting Intern");
        arrayList.add("Accounting Manager");
        arrayList.add("Accounts Paybale");
        arrayList.add("Actor");
        arrayList.add("Admissions Officers");
        arrayList.add("Administrator");
        arrayList.add("Admissions Counselor");
        arrayList.add("Air Traffic Controller");
        arrayList.add("Analyst");
        arrayList.add("Architect");
        arrayList.add("Art Teacher");
        arrayList.add("Artist");
        arrayList.add("Assistant Buyer");
        arrayList.add("Assistant Principal");
        arrayList.add("Attorney");
        arrayList.add("Author Assistant");
        arrayList.add("Autocentre Manager");
        arrayList.add("Automotive Sales Manager");
        arrayList.add("Babysitter");
        arrayList.add("Banker");
        arrayList.add("Bank Manager");
        arrayList.add("Barista");
        arrayList.add("Bartender");
        arrayList.add("Bid Writer");
        arrayList.add("Bookkeeper");
        arrayList.add("Business Analyst");
        arrayList.add("Business Support Manager");
        arrayList.add("Careers Advisor");
        arrayList.add("Caregiver");
        arrayList.add("Case Manager");
        arrayList.add("Cashier");
        arrayList.add("Chef");
        arrayList.add("Chiropractor");
        arrayList.add("Civil Engineer");
        arrayList.add("Coach");
        arrayList.add("Consultant");
        arrayList.add("Controller");
        arrayList.add("Copywriter");
        arrayList.add("Cosmetologist");
        arrayList.add("Counselor");
        arrayList.add("Credit Analyst");
        arrayList.add("Curriculum Developer");
        arrayList.add("Custodian");
        arrayList.add("Customer Experience Manager");
        arrayList.add("Customer Service Respresentative");
        arrayList.add("Data Analyst");
        arrayList.add("Data Science");
        arrayList.add("Database Analyst");
        arrayList.add("Dental Assistant");
        arrayList.add("Dental Hygienist");
        arrayList.add("Dentist");
        arrayList.add("Designer");
        arrayList.add("Design Manager");
        arrayList.add("Distribution Manager");
        arrayList.add("Diversity Manager");
        arrayList.add("Ecologist");
        arrayList.add("Electrical Engineer");
        arrayList.add("Engineer");
        arrayList.add("Engineering Intern");
        arrayList.add("Esthetician");
        arrayList.add("Event Coordinator");
        arrayList.add("Event Manager");
        arrayList.add("Event Planner");
        arrayList.add("Executive Assistant");
        arrayList.add("Finance Intern");
        arrayList.add("Finance Analyst");
        arrayList.add("Fleet Manager");
        arrayList.add("Firefighter");
        arrayList.add("Flight Attendant");
        arrayList.add("Freelance Writer");
        arrayList.add("Furniture Design");
        arrayList.add("Graduate Assistant");
        arrayList.add("Graphic Designer");
        arrayList.add("Guest Relations Manager");
        arrayList.add("Hair Stylist");
        arrayList.add("Head of Marketing");
        arrayList.add("Head of Operation");
        arrayList.add("Housekeeper");
        arrayList.add("Human Resources Assistant");
        arrayList.add("Human Resources Generalist");
        arrayList.add("Human Resources Manager");
        arrayList.add("Intern ");
        arrayList.add("IT Project Manager");
        arrayList.add("Laboratory Technician");
        arrayList.add("Lead Case Manager");
        arrayList.add("Legal Assistant");
        arrayList.add("Legal Intern ");
        arrayList.add("Libratian");
        arrayList.add("Library Assistant ");
        arrayList.add("Management Consultant");
        arrayList.add("Manager");
        arrayList.add("Marketer");
        arrayList.add("Marketing");
        arrayList.add("Marketing Coordinator");
        arrayList.add("Marketing Intern");
        arrayList.add("Marketing Manager");
        arrayList.add("Message Therapist");
        arrayList.add("Mechanical Engineer");
        arrayList.add("Medical Assistant ");
        arrayList.add("Medical Receptionist");
        arrayList.add("Microbiologist");
        arrayList.add("Nanny");
        arrayList.add("Nurse Practitioner");
        arrayList.add("Nursing Assistant");
        arrayList.add("Nursing Student ");
        arrayList.add("Occupational Therapist");
        arrayList.add("Office Administrator");
        arrayList.add("Office Assistant");
        arrayList.add("Office Manager");
        arrayList.add("Operation Manager");
        arrayList.add("Outreach Worker");
        arrayList.add("Paralegal");
        arrayList.add("Paraprofessional");
        arrayList.add("Partnership Manager");
        arrayList.add("Parts Advisor");
        arrayList.add("Personal Assistant");
        arrayList.add("Personal Banker");
        arrayList.add("Pest Control");
        arrayList.add("Pharmacist");
        arrayList.add("Pharmacy Technician");
        arrayList.add("Photographer");
        arrayList.add("Physician");
        arrayList.add("Physician Assistant");
        arrayList.add("Pilot");
        arrayList.add("Platform Engineer");
        arrayList.add("Police Officer");
        arrayList.add("Preshool Teacher");
        arrayList.add("Product Manager");
        arrayList.add("Production Assistant");
        arrayList.add("Program Coordinator");
        arrayList.add("Program Manager");
        arrayList.add("Project Coordinator");
        arrayList.add("Project Manager");
        arrayList.add("Property Manager");
        arrayList.add("Psychologist");
        arrayList.add("Quality Manager");
        arrayList.add("Receptionist");
        arrayList.add("Recruiter");
        arrayList.add("Recruitment Coordinator");
        arrayList.add("Recruitment Manager");
        arrayList.add("Regional Operational Manager");
        arrayList.add("Registered Nurse");
        arrayList.add("Research Assistant");
        arrayList.add("Resident Assistant ");
        arrayList.add("Residentail Assistant");
        arrayList.add("Restaurant Manager");
        arrayList.add("Risk Manager");
        arrayList.add("Sales");
        arrayList.add("Sales Manager");
        arrayList.add("Sales Representative");
        arrayList.add("School Counselor");
        arrayList.add("School Social Worker");
        arrayList.add("Scientist");
        arrayList.add("Secretary");
        arrayList.add("Secutity");
        arrayList.add("Security Guard");
        arrayList.add("Security Office");
        arrayList.add("Senior Social Worker");
        arrayList.add("Server");
        arrayList.add("Server Coordinator");
        arrayList.add("Shift Supervisor");
        arrayList.add("Shift Technician");
        arrayList.add("Showroom Manager");
        arrayList.add("Social Media Marketing Manager");
        arrayList.add("Social Worker");
        arrayList.add("Software Developer");
        arrayList.add("Software Engineer");
        arrayList.add("Special Education Teacher");
        arrayList.add("Speech Language Pathologist");
        arrayList.add("Substitute Teacher");
        arrayList.add("Supervisor");
        arrayList.add("Teacher Aide");
        arrayList.add("Teaching Assistant");
        arrayList.add("Teachnical Support");
        arrayList.add("Training Developer");
        arrayList.add("Veterinarian");
        arrayList.add("Veterinary Assistant");
        arrayList.add("Veterinary Technician");
        arrayList.add("Warehouse");
        arrayList.add("Web Developer");
        arrayList.add("Web Manager");
        arrayList.add("Welder");
        arrayList.add("Writer");*/

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nameOfLettreDeMotivation = arrayList.get(position);
        holder.titel.setText(nameOfLettreDeMotivation);
        String nameOfLetterDeMotivation = (String) holder.titel.getText();

        switch (nameOfLetterDeMotivation) {
            case "Acadimic Advisor":
                // Add your specific action here
                break;
            case "Acadimic Coach":
                // Add your specific action here
                break;
            case "Acadimic Coordinator":
                // Add your specific action here
                break;
            case "Acadimic Tutor":
                // Add your specific action here
                break;
            case "Account Executive":
                // Add your specific action here
                break;
            case "Account Manager":
                // Add your specific action here
                break;
            case "Accountant":
                // Add your specific action here
                break;
            case "Accounting Intern":
                // Add your specific action here
                break;
            case "Accounting Manager":
                // Add your specific action here
                break;
            case "Accounts Paybale":
                // Add your specific action here
                break;
            case "Actor":
                // Add your specific action here
                break;
            case "Admissions Officers":
                // Add your specific action here
                break;
            case "Administrator":
                // Add your specific action here
                break;
            case "Admissions Counselor":
                // Add your specific action here
                break;
            case "Air Traffic Controller":
                // Add your specific action here
                break;
            case "Analyst":
                // Add your specific action here
                break;
            case "Architect":
                // Add your specific action here
                break;
            case "Art Teacher":
                // Add your specific action here
                break;
            case "Artist":
                // Add your specific action here
                break;
            case "Assistant Buyer":
                // Add your specific action here
                break;
            case "Assistant Principal":
                // Add your specific action here
                break;
            case "Attorney":
                // Add your specific action here
                break;
            case "Author Assistant":
                // Add your specific action here
                break;
            case "Autocentre Manager":
                // Add your specific action here
                break;
            case "Automotive Sales Manager":
                // Add your specific action here
                break;
            case "Babysitter":
                // Add your specific action here
                break;
            case "Banker":
                // Add your specific action here
                break;
            case "Bank Manager":
                // Add your specific action here
                break;
            case "Barista":
                // Add your specific action here
                break;
            case "Bartender":
                // Add your specific action here
                break;
            case "Bid Writer":
                // Add your specific action here
                break;
            case "Bookkeeper":
                // Add your specific action here
                break;
            case "Business Analyst":
                // Add your specific action here
                break;
            case "Business Support Manager":
                // Add your specific action here
                break;
            case "Careers Advisor":
                // Add your specific action here
                break;
            case "Caregiver":
                // Add your specific action here
                break;
            case "Case Manager":
                // Add your specific action here
                break;
            case "Cashier":
                // Add your specific action here
                break;
            case "Chef":
                // Add your specific action here
                break;
            case "Chiropractor":
                // Add your specific action here
                break;
            case "Civil Engineer":
                // Add your specific action here
                break;
            case "Coach":
                // Add your specific action here
                break;
            case "Consultant":
                // Add your specific action here
                break;
            case "Controller":
                // Add your specific action here
                break;
            case "Copywriter":
                // Add your specific action here
                break;
            case "Cosmetologist":
                // Add your specific action here
                break;
            case "Counselor":
                // Add your specific action here
                break;
            case "Credit Analyst":
                // Add your specific action here
                break;
            case "Curriculum Developer":
                // Add your specific action here
                break;
            case "Custodian":
                // Add your specific action here
                break;
            case "Customer Experience Manager":
                // Add your specific action here
                break;
            case "Customer Service Representative":
                // Add your specific action here
                break;
            case "Data Analyst":
                // Add your specific action here
                break;
            case "Data Scientist":
                // Add your specific action here
                break;
            case "Database Analyst":
                // Add your specific action here
                break;
            case "Dental Assistant":
                // Add your specific action here
                break;
            case "Dental Hygienist":
                // Add your specific action here
                break;
            case "Dentist":
                // Add your specific action here
                break;
            case "Designer":
                // Add your specific action here
                break;
            case "Design Manager":
                // Add your specific action here
                break;
            case "Distribution Manager":
                // Add your specific action here
                break;
            case "Diversity Manager":
                // Add your specific action here
                break;
            case "Ecologist":
                // Add your specific action here
                break;
            case "Electrical Engineer":
                // Add your specific action here
                break;
            case "Engineer":
                // Add your specific action here
                break;
            case "Engineering Intern":
                // Add your specific action here
                break;
            case "Esthetician":
                // Add your specific action here
                break;
            case "Event Coordinator":
                // Add your specific action here
                break;
            case "Event Manager":
                // Add your specific action here
                break;
            case "Event Planner":
                // Add your specific action here
                break;
            case "Executive Assistant":
                // Add your specific action here
                break;
            case "Finance Intern":
                // Add your specific action here
                break;
            case "Finance Analyst":
                // Add your specific action here
                break;
            case "Fleet Manager":
                // Add your specific action here
                break;
            case "Firefighter":
                // Add your specific action here
                break;
            case "Flight Attendant":
                // Add your specific action here
                break;
            case "Freelance Writer":
                // Add your specific action here
                break;
            case "Furniture Designer":
                // Add your specific action here
                break;
            case "Graduate Assistant":
                // Add your specific action here
                break;
            case "Graphic Designer":
                // Add your specific action here
                break;
            case "Guest Relations Manager":
                // Add your specific action here
                break;
            case "Hair Stylist":
                // Add your specific action here
                break;
            case "Head of Marketing":
                // Add your specific action here
                break;
            case "Head of Operation":
                // Add your specific action here
                break;
            case "Housekeeper":
                // Add your specific action here
                break;
            case "Human Resources Assistant":
                // Add your specific action here
                break;
            case "Human Resources Generalist":
                // Add your specific action here
                break;
            case "Human Resources Manager":
                // Add your specific action here
                break;
            case "Intern":
                // Add your specific action here
                break;
            case "IT Project Manager":
                // Add your specific action here
                break;
            case "Laboratory Technician":
                // Add your specific action here
                break;
            case "Lead Case Manager":
                // Add your specific action here
                break;
            case "Legal Assistant":
                // Add your specific action here
                break;
            case "Legal Intern":
                // Add your specific action here
                break;
            case "Librarian":
                // Add your specific action here
                break;
            case "Library Assistant":
                // Add your specific action here
                break;
            case "Management Consultant":
                // Add your specific action here
                break;
            case "Manager":
                // Add your specific action here
                break;
            case "Marketer":
                // Add your specific action here
                break;
            case "Marketing":
                // Add your specific action here
                break;
            case "Marketing Coordinator":
                // Add your specific action here
                break;
            case "Marketing Intern":
                // Add your specific action here
                break;
            case "Marketing Manager":
                // Add your specific action here
                break;
            case "Massage Therapist":
                // Add your specific action here
                break;
            case "Mechanical Engineer":
                // Add your specific action here
                break;
            case "Medical Assistant":
                // Add your specific action here
                break;
            case "Medical Receptionist":
                // Add your specific action here
                break;
            case "Microbiologist":
                // Add your specific action here
                break;
            case "Nanny":
                // Add your specific action here
                break;
            case "Nurse Practitioner":
                // Add your specific action here
                break;
            case "Nursing Assistant":
                // Add your specific action here
                break;
            case "Nursing Student":
                // Add your specific action here
                break;
            case "Occupational Therapist":
                // Add your specific action here
                break;
            case "Office Administrator":
                // Add your specific action here
                break;
            case "Office Assistant":
                // Add your specific action here
                break;
            case "Office Manager":
                // Add your specific action here
                break;
            case "Operation Manager":
                // Add your specific action here
                break;
            case "Outreach Worker":
                // Add your specific action here
                break;
            case "Paralegal":
                // Add your specific action here
                break;
            case "Paraprofessional":
                // Add your specific action here
                break;
            case "Partnership Manager":
                // Add your specific action here
                break;
            case "Parts Advisor":
                // Add your specific action here
                break;
            case "Personal Assistant":
                // Add your specific action here
                break;
            case "Personal Banker":
                // Add your specific action here
                break;
            case "Pest Control":
                // Add your specific action here
                break;
            case "Pharmacist":
                // Add your specific action here
                break;
            case "Pharmacy Technician":
                // Add your specific action here
                break;
            case "Photographer":
                // Add your specific action here
                break;
            case "Physician":
                // Add your specific action here
                break;
            case "Physician Assistant":
                // Add your specific action here
                break;
            case "Pilot":
                // Add your specific action here
                break;
            case "Platform Engineer":
                // Add your specific action here
                break;
            case "Police Officer":
                // Add your specific action here
                break;
            case "Preschool Teacher":
                // Add your specific action here
                break;
            case "Product Manager":
                // Add your specific action here
                break;
            case "Production Assistant":
                // Add your specific action here
                break;
            case "Program Coordinator":
                // Add your specific action here
                break;
            case "Program Manager":
                // Add your specific action here
                break;
            case "Project Coordinator":
                // Add your specific action here
                break;
            case "Project Manager":
                // Add your specific action here
                break;
            case "Property Manager":
                // Add your specific action here
                break;
            case "Psychologist":
                // Add your specific action here
                break;
            case "Quality Manager":
                // Add your specific action here
                break;
            case "Receptionist":
                // Add your specific action here
                break;
            case "Recruiter":
                // Add your specific action here
                break;
            case "Recruitment Coordinator":
                // Add your specific action here
                break;
            case "Recruitment Manager":
                // Add your specific action here
                break;
            case "Regional Operational Manager":
                // Add your specific action here
                break;
            case "Registered Nurse":
                // Add your specific action here
                break;
            case "Research Assistant":
                // Add your specific action here
                break;
            case "Resident Assistant":
                // Add your specific action here
                break;
            case "Residential Assistant":
                // Add your specific action here
                break;
            case "Restaurant Manager":
                // Add your specific action here
                break;
            case "Risk Manager":
                // Add your specific action here
                break;
            case "Sales":
                // Add your specific action here
                break;
            case "Sales Manager":
                // Add your specific action here
                break;
            case "Sales Representative":
                // Add your specific action here
                break;
            case "School Counselor":
                // Add your specific action here
                break;
            case "School Social Worker":
                // Add your specific action here
                break;
            case "Scientist":
                // Add your specific action here
                break;
            case "Secretary":
                // Add your specific action here
                break;
            case "Security":
                // Add your specific action here
                break;
            case "Security Guard":
                // Add your specific action here
                break;
            case "Security Officer":
                // Add your specific action here
                break;
            case "Senior Social Worker":
                // Add your specific action here
                break;
            case "Server":
                // Add your specific action here
                break;
            case "Server Coordinator":
                // Add your specific action here
                break;
            case "Shift Supervisor":
                // Add your specific action here
                break;
            case "Shift Technician":
                // Add your specific action here
                break;
            case "Showroom Manager":
                // Add your specific action here
                break;
            case "Social Media Marketing Manager":
                // Add your specific action here
                break;
            case "Social Worker":
                // Add your specific action here
                break;
            case "Software Developer":
                // Add your specific action here
                break;
            case "Software Engineer":
                // Add your specific action here
                break;
            case "Special Education Teacher":
                // Add your specific action here
                break;
            case "Speech Language Pathologist":
                // Add your specific action here
                break;
            case "Substitute Teacher":
                // Add your specific action here
                break;
            case "Supervisor":
                // Add your specific action here
                break;
            case "Teacher Aide":
                // Add your specific action here
                break;
            case "Teaching Assistant":
                // Add your specific action here
                break;
            case "Technical Support":
                // Add your specific action here
                break;
            case "Training Developer":
                // Add your specific action here
                break;
            case "Veterinarian":
                // Add your specific action here
                break;
            case "Veterinary Assistant":
                // Add your specific action here
                break;
            case "Veterinary Technician":
                // Add your specific action here
                break;
            case "Warehouse Worker":
                // Add your specific action here
                break;
            case "Web Developer":
                // Add your specific action here
                break;
            case "Web Manager":
                // Add your specific action here
                break;
            case "Welder":
                // Add your specific action here
                break;
            case "Writer":
                // Add your specific action here
                break;
            default:
                // Add your default action here
                break;
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titel = itemView.findViewById(R.id.title);
        }
    }
}
