select name from chat.Users as U where ((select count(user_id) where user_id=U.id) > 3);