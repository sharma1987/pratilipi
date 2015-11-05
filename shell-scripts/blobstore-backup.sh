export TZ=Asia/Calcutta

hour=$(date +%H)
minute=$(date +%M)

hour=${hour#0}
minute=${minute#0}

bucket_static="gs://static.pratilipi.com"
bucket_backup="gs://backup.pratilipi.com"
bucket_public="gs://public.pratilipi.com"

backup_folder="$(date +%Y-%m-%d)/static.pratilipi.com-$(date +%Y-%m-%d-%H:%M-IST)"

if [ $hour -ge 8 -a $hour -le 20 -a $minute -eq 0 ]
then
	{ #try block
		gsutil -m cp -r $bucket_static "$bucket_backup/$backup_folder"
		gsutil -m du -s $bucket_static "$bucket_backup/$backup_folder" 2>&1 | logger
		gsutil -m du -sh $bucket_static $bucket_public $bucket_backup 2>&1 | logger
		logger "_BLOBSTORE_BACKUP_SUCCEEDED_"
	} || { #catch block
		logger "_BLOBSTORE_BACKUP_FAILED_"
	}
fi