insert into account (id, created_at, updated_at)
values (1, now(), now());

insert into boj_challenge (id, username, account_id, created_at, updated_at)
values (1, 'armhf', 1, now(), now());

insert into github_credential (id, access_token, username, repository, account_id, created_at, updated_at)
values (1, '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', 'dygma0', 'diary', 1, now(), now());
