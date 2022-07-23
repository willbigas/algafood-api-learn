ALTER TABLE forma_pagamento ADD data_atualizacao DATETIME NULL;
update forma_pagamento set data_atualizacao = utc_timestamp;
alter table forma_pagamento modify data_atualizacao datetime not null;


