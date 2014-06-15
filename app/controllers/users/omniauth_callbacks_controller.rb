class Users::OmniauthCallbacksController < Devise::OmniauthCallbacksController
  def facebook
    # You need to implement the method below in your model
    @user = User.find_for_facebook_oauth(request.env["omniauth.auth"], current_user)

    if @user.persisted?
      flash[:notice] = I18n.t "devise.omniauth_callbacks.success", :kind => "Facebook"
      logger.debug("found")
      # sign_in_and_redirect @user, :event => :authentication
      redirect_to root_path
    else
      session["devise.facebook_data"] = request.env["omniauth.auth"]
      logger.debug("not")
      # redirect_to new_user_registration_url
    end
  end

end