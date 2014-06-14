class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.string :discription
      t.date :ivent_date
      t.integer :budget
      t.string :departure_place
      t.text :detail

      t.timestamps
    end
  end
end
