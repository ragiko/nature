class HomeController < ApplicationController
  def index
  	@posts = Post.all
  	@events = Event.all
  end
end
