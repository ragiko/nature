class Users::OmniauthCallbacksController < Devise::OmniauthCallbacksController
  def facebook
    # You need to implement the method below in your model
    logger.debug("samplesample1")
    @user = User.find_for_facebook_oauth(request.env["omniauth.auth"], current_user)

    logger.debug(@user)
  end

end