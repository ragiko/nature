class Event < ActiveRecord::Base
	mount_uploader :image, ImageUploader
	has_many :participants

	# イベントから参加者全員を返す
	def get_participants(event)
		return event.participants.all 
	end
end
