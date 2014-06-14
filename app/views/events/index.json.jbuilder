json.array!(@events) do |event|
  json.extract! event, :id, :discription, :ivent_date, :budget, :departure_place, :detail
  json.url event_url(event, format: :json)
end
