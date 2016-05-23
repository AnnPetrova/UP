select U.name, M.text, M.date from chat.Messages as M left join chat.Users as U on M.user_id = U.id where length(M.text) > 140;