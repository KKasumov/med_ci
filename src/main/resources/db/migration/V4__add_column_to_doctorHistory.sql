alter table employee_history add column add_talon_auto boolean not null default false,
add constraint chk_employee_history_add_talon_auto check (dtype = 'DoctorHistory');